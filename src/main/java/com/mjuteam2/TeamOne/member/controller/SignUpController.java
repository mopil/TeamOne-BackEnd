package com.mjuteam2.TeamOne.member.controller;

import com.mjuteam2.TeamOne.common.dto.ApiResponse;
import com.mjuteam2.TeamOne.common.dto.BooleanResponse;
import com.mjuteam2.TeamOne.common.error.ErrorCode;
import com.mjuteam2.TeamOne.common.error.ErrorDto;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.dto.EmailDto;
import com.mjuteam2.TeamOne.member.dto.SignUpForm;
import com.mjuteam2.TeamOne.member.exception.SignUpException;
import com.mjuteam2.TeamOne.member.service.EmailService;
import com.mjuteam2.TeamOne.member.service.SignUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpForm signUpForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
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
        return ApiResponse.success(new EmailDto(userEmail, authToken));
    }

    /**
     * 인증번호 확인
     */
    @GetMapping("/auth/{userEmail}/{authToken}")
    public ResponseEntity<?> authTokenCheck(@PathVariable String userEmail, @PathVariable String authToken) {
        log.info("email = {}, authToken = {}", userEmail, authToken);
        log.info("authTokenList : {}", signUpService.getAuthTokenStorage());
        if (!signUpService.authTokenCheck(userEmail, authToken)) {
            log.error("Error : authToken mismatched");
            return ApiResponse.badRequest(new ErrorDto(ErrorCode.AUTH_TOKEN_ERROR, "authToken mismatched"));
        }
        log.info("Success : authToken matched");
        return ApiResponse.success(new EmailDto(userEmail, authToken));
    }


    /**
     *  예외 처리
     */
    // 비밀번호 불일치, 중복 이메일
    @ExceptionHandler(SignUpException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> signUpExHandle(SignUpException e) {
        log.error("[exceptionHandle] ex", e);
        return ApiResponse.badRequest(new ErrorDto(ErrorCode.SIGN_UP_ERROR, e.getMessage()));
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> otherExHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return ApiResponse.badRequest(new ErrorDto(ErrorCode.COMMON_ERROR, e.getMessage()));
    }

}
