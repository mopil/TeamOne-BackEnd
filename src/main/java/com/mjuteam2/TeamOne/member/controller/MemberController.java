package com.mjuteam2.TeamOne.member.controller;

import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.dto.MemberResponse;
import com.mjuteam2.TeamOne.member.dto.PasswordUpdateForm;
import com.mjuteam2.TeamOne.member.service.MemberService;
import com.mjuteam2.TeamOne.util.dto.BoolResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.mjuteam2.TeamOne.util.dto.ErrorResponse.convertJson;
import static com.mjuteam2.TeamOne.util.dto.RestResponse.badRequest;
import static com.mjuteam2.TeamOne.util.dto.RestResponse.success;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 하나 조회 id = PK, userId = 진짜 유저 아이디
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findByUserId(@PathVariable Long id) {
        MemberResponse findMember = memberService.findByUserId(id);
        log.info("select member = {}", findMember);
        return success(findMember);
    }

    /**
     * 회원 정보 수정
     * 평점(star) 변경은 rating 도메인에
     */
    // 닉네임 변경
    @PutMapping("/nickname/{newNickname}")
    public ResponseEntity<?> updateNickname(HttpServletRequest request, @PathVariable String newNickname) throws LoginException {
        Member loginMember = memberService.getLoginMember(request);
        memberService.updateNickname(loginMember, newNickname);
        return success(loginMember);
    }

    // 비밀번호 변경
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(HttpServletRequest request,
                                            @Valid @RequestBody PasswordUpdateForm form,
                                            BindingResult bindingResult) throws LoginException {
        if (bindingResult.hasErrors()) {
            log.error("Password Update Error = {}", bindingResult.getFieldErrors());
            return badRequest(convertJson(bindingResult.getFieldErrors()));
        }
        Member loginMember = memberService.getLoginMember(request);
        memberService.updatePassword(loginMember, form.getNewPassword());
        return success(loginMember);
    }

    // 포인트 변경 (랭킹 포인트)
    @PutMapping("/point/{amount}")
    public ResponseEntity<?> updatePoint(HttpServletRequest request, @PathVariable Integer amount) throws LoginException {
        Member loginMember = memberService.getLoginMember(request);
        memberService.addPoint(loginMember, amount);
        return success(loginMember);
    }

    // 자기소개 변경
    @PutMapping("/introduce/{newIntroduce}")
    public ResponseEntity<?> updateIntroduce(HttpServletRequest request, @PathVariable String newIntroduce) throws LoginException {
        Member loginMember = memberService.getLoginMember(request);
        memberService.updateIntroduce(loginMember, newIntroduce);
        return success(loginMember);
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("")
    public ResponseEntity<?> deleteUser(HttpServletRequest request) throws LoginException {
        Member loginMember = memberService.getLoginMember(request);
        memberService.deleteMember(loginMember.getId(), request);
        return success(new BoolResponse(true));
    }

}
