package com.mjuteam2.TeamOne.member.controller;

import com.mjuteam2.TeamOne.common.dto.ApiResponse;
import com.mjuteam2.TeamOne.common.dto.BooleanResponse;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.dto.EmailResponse;
import com.mjuteam2.TeamOne.member.dto.SignUpForm;
import com.mjuteam2.TeamOne.member.service.EmailService;
import com.mjuteam2.TeamOne.member.service.SignUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class SignUpController {

    private final SignUpService signUpService;
    private final EmailService emailService;

    /**
     * 회원 가입
     * @param signUpForm 클라이언트에서 받아온 회원가입 폼 DTO
     * @param bindingResult 에러 발생시 다 잡아주는 녀석
     * @return 성공시 새로운 멤버 객체 JSON으로 반환, 실패시 오류(검증 실패한 부분)을 반환
     */

    @PostMapping("/new")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpForm signUpForm, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(bindingResult.getFieldErrors());
        }

        Member newMember = signUpService.signUp(signUpForm);
        return ApiResponse.success(newMember);
    }

    /**
     * 닉네임 중복 체크
     */
    @GetMapping("/nickname-check/{nickname}")
    public ResponseEntity<?> nicknameCheck(@PathVariable String nickname) {
        return ApiResponse.success(new BooleanResponse(signUpService.nicknameCheck(nickname)));
    }


    /**
     * 유저 아이디 중복 체크
     */
    @GetMapping("/id-check/{id}")
    public ResponseEntity<?> idCheck(@PathVariable String id) {
        return ApiResponse.success(new BooleanResponse(signUpService.idCheck(id)));
    }

    /**
     * 인증번호 보내기
     * @mju.ac.kr 앞 아이디만 받아와서 메일 생성
     */
    @PostMapping("/auth/{userEmail}")
    public ResponseEntity<?> authMailSend(@PathVariable String userEmail) {
        String authToken = signUpService.setAuthToken(userEmail);
        emailService.sendMail(userEmail, authToken);
        log.info("Success : email send # address = {}, token = {}", userEmail+"@mju.ac.kr", authToken);
        // 메일이 안보내졌을때 예외를 던지는데 예외처리를 해줘야함
        return ApiResponse.success(new EmailResponse(userEmail, authToken, EmailResponse.EmailProcessResult.SUCCESS, "auth mail sent"));
    }

    /**
     * 인증번호 확인
     */
    @GetMapping("/auth/{userEmail}/{authToken}")
    public ResponseEntity<?> authTokenCheck(@PathVariable String userEmail, @PathVariable String authToken) {
        log.info("email = {}, authToken = {}", userEmail, authToken);
        log.info("authTokenList : {}", signUpService.getAuthTokenStorage());
        if (!signUpService.authTokenCheck(userEmail, authToken)) {
            log.info("Error : authToken mismatched");
            return ApiResponse.badRequest(new EmailResponse(userEmail, authToken, EmailResponse.EmailProcessResult.FAIL, "authToken mismatched"));
        }
        log.info("Success : authToken matched");
        return ApiResponse.success(new EmailResponse(userEmail, authToken, EmailResponse.EmailProcessResult.SUCCESS, "authToken matched"));
    }
}
