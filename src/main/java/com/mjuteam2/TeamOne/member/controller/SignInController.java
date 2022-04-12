package com.mjuteam2.TeamOne.member.controller;

import com.mjuteam2.TeamOne.common.dto.ApiResponse;
import com.mjuteam2.TeamOne.common.error.ErrorDto;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.dto.SignInForm;
import com.mjuteam2.TeamOne.member.service.SignInService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class SignInController {

    private final SignInService signInService;

    /**
     * 로그인
     * @param loginForm     로그인 관련 DTO
     * @param bindingResult 검증 관련
     * @return 성공시 로그인 맴버 객체 JSON으로 반환
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm loginForm, BindingResult bindingResult, HttpServletRequest request) throws LoginException {

        if (bindingResult.hasErrors()) {
            log.error("Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }

        Member loginMember = signInService.Login(loginForm);

        // 로그인 성공시
        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();

        // 세선에 로그인 회원정보 보관
        session.setAttribute("member_info", loginMember);
        return ApiResponse.success(loginMember);
    }

    /**
     * 로그아웃
     * 세션 삭제해서 로그아웃 진행
     */

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        // 세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ApiResponse.success("logout");
    }
}
