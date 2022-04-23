package com.mjuteam2.TeamOne.member.controller;

import com.mjuteam2.TeamOne.member.config.SessionConst;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.dto.FindMemberForm;
import com.mjuteam2.TeamOne.member.dto.MemberResponse;
import com.mjuteam2.TeamOne.member.dto.ResetPasswordForm;
import com.mjuteam2.TeamOne.member.dto.SignInForm;
import com.mjuteam2.TeamOne.member.service.SignInService;
import com.mjuteam2.TeamOne.util.dto.BoolResponse;
import com.mjuteam2.TeamOne.util.dto.ErrorResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.mjuteam2.TeamOne.util.dto.RestResponse.badRequest;
import static com.mjuteam2.TeamOne.util.dto.RestResponse.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class SignInController {

    private final SignInService signInService;

    /**
     * 로그인
     */
    @ApiOperation(value = "로그인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공, 회원정보와 세션 ID 리턴"),
            @ApiResponse(code = 400, message = "로그인 실패")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm loginForm,
                                   BindingResult bindingResult,
                                   HttpServletRequest request) throws LoginException {
        if (bindingResult.hasErrors()) {
            log.error("SignIn Errors = {}", bindingResult.getFieldErrors());
            return badRequest(ErrorResponse.convertJson(bindingResult.getFieldErrors()));
        }
        MemberResponse memberResponse = signInService.login(loginForm, request);
        log.info("member login = {}", memberResponse);
        return success(memberResponse);
    }

    /**
     * 로그아웃
     * 세션 삭제해서 로그아웃 진행
     */
    @ApiOperation(value = "로그아웃")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그아웃 성공"),
            @ApiResponse(code = 400, message = "로그아웃 실패")
    })
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        log.info("member logout = {}", request.getAttribute(SessionConst.LOGIN_MEMBER));
        signInService.logout(request);
        return success(new BoolResponse(true));
    }

    /**
     * 사용자 아이디 찾기
     */
    @ApiOperation(value = "사용자 아이디 찾기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "사용자 아이디 찾기 성공"),
            @ApiResponse(code = 400, message = "사용자 아이디 찾기 실패")
    })
    @PostMapping("/id")
    public ResponseEntity<?> findUserId(@Valid @RequestBody FindMemberForm form) {
        Member findMember = signInService.findByUserId(form);
        return success(findMember);
    }

    /**
     * 비밀번호 재설정
     */
    @ApiOperation(value = "비밀번호 재설정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "재설정 성공"),
            @ApiResponse(code = 400, message = "재설정 실패")
    })
    @PostMapping("/password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordForm form) {
        signInService.resetPassword(form);
        return success(new BoolResponse(true));
    }


}
