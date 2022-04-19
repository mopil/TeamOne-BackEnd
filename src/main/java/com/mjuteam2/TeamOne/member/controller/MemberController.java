package com.mjuteam2.TeamOne.member.controller;

import com.mjuteam2.TeamOne.util.dto.ApiResponse;
import com.mjuteam2.TeamOne.util.dto.BooleanResponse;
import com.mjuteam2.TeamOne.util.exception.ErrorCode;
import com.mjuteam2.TeamOne.util.exception.ErrorDto;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.dto.PasswordUpdateForm;
import com.mjuteam2.TeamOne.member.config.Login;
import com.mjuteam2.TeamOne.member.service.MemberService;
import com.mjuteam2.TeamOne.member.service.SignInService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;
    private final SignInService signInService;

    /**
     * 회원 하나 조회 id = PK, userId = 진짜 유저 아이디
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findByUserId(@PathVariable Long id) {
        Member foundMember = memberService.findByUserId(id);
        log.info("select member = {}", foundMember);
        return ApiResponse.success(foundMember);
    }

    /**
     * 회원 정보 수정
     */
    // 닉네임 변경
    @PutMapping("/nickname/{newNickname}")
    public ResponseEntity<?> updateNickname(@Login Member loginMember, @PathVariable String newNickname) throws LoginException {
        signInService.loginCheck(loginMember);
        return ApiResponse.success(memberService.updateNickname(loginMember.getId(), newNickname));
    }

    // 비밀번호 변경
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@Login Member loginMember,
                                            @Valid @RequestBody PasswordUpdateForm form,
                                            BindingResult bindingResult) throws LoginException {
        if (bindingResult.hasErrors()) {
            log.error("Password Update Error = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }
        signInService.loginCheck(loginMember);
        return ApiResponse.success(memberService.updatePassword(loginMember.getId(), form.getNewPassword()));
    }

    // 평점(star) 변경
    @PutMapping("/star/{newStar}")
    public ResponseEntity<?> updateStar(@Login Member loginMember, @PathVariable Double newStar) throws LoginException {
        signInService.loginCheck(loginMember);
        return ApiResponse.success(memberService.updateStar(loginMember.getId(), newStar));
    }

    // 포인트 변경 (랭킹 포인트)
    @PutMapping("/point/{newPoint}")
    public ResponseEntity<?> updatePoint(@Login Member loginMember, @PathVariable Integer newPoint) throws LoginException {
        signInService.loginCheck(loginMember);
        return ApiResponse.success(memberService.updatePoint(loginMember.getId(), newPoint));
    }

    // 자기소개 변경
    @PutMapping("/introduce/{newIntroduce}")
    public ResponseEntity<?> updateIntroduce(@Login Member loginMember, @PathVariable String newIntroduce) throws LoginException {
        signInService.loginCheck(loginMember);
        return ApiResponse.success(memberService.updateIntroduce(loginMember.getId(), newIntroduce));
    }


    /**
     * 회원 탈퇴
     */
    @DeleteMapping("")
    public ResponseEntity<?> deleteUser(@Login Member loginMember, HttpServletRequest request) throws LoginException {
        signInService.loginCheck(loginMember);
        memberService.deleteMember(loginMember.getId(), request);
        return ApiResponse.success(new BooleanResponse(true));
    }


    /**
     * 예외 처리
     */
    // 로그인 관련 예외
    @ExceptionHandler(LoginException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> loginExHandle(LoginException e) {
        log.error("[exceptionHandle] ex", e);
        return ApiResponse.badRequest(new ErrorDto(ErrorCode.LOGIN_ERROR, "인증 거부 : 로그인 안 됨."));
    }
}
