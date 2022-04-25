package com.mjuteam2.TeamOne.member.controller;

import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.dto.EmailResponse;
import com.mjuteam2.TeamOne.member.dto.SignUpForm;
import com.mjuteam2.TeamOne.member.exception.SignUpException;
import com.mjuteam2.TeamOne.member.service.EmailService;
import com.mjuteam2.TeamOne.member.service.SignUpService;
import com.mjuteam2.TeamOne.util.dto.BoolResponse;
import com.mjuteam2.TeamOne.util.dto.ErrorResponse;
import com.mjuteam2.TeamOne.util.exception.ErrorCode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.mjuteam2.TeamOne.util.dto.RestResponse.badRequest;
import static com.mjuteam2.TeamOne.util.dto.RestResponse.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class SignUpController {

    private final SignUpService signUpService;
    private final EmailService emailService;

    /**
     * 회원 가입
     */
    @ApiOperation(value="회원 가입")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 가입 성공"),
            @ApiResponse(code = 400, message = "회원 가입 실패")
    })
    @PostMapping("/new")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpForm signUpForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("SignUp Errors = {}", bindingResult.getFieldErrors());
            return badRequest(ErrorResponse.convertJson(bindingResult.getFieldErrors()));
        }

        Member newMember = signUpService.signUp(signUpForm);
        log.info("New member is signed up = {}", newMember.getUserId());
        return success(newMember);
    }

    /**
     * 닉네임 중복 체크
     */
    @ApiOperation(value="회원 가입 검증 : 닉네임 중복 체크")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공 : 닉네임이 중복 되지 않음"),
            @ApiResponse(code = 400, message = "실패 : 닉네임이 중복 됨")
    })
    @GetMapping("/nickname-check/{nickname}")
    public ResponseEntity<?> nicknameCheck(@PathVariable String nickname) {
        return success(new BoolResponse(signUpService.nicknameCheck(nickname)));
    }


    /**
     * 유저 아이디 중복 체크
     */
    @ApiOperation(value="회원 가입 검증 : 유저 아이디 중복 체크")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공 : 유저 아이디가 중복 되지 않음"),
            @ApiResponse(code = 400, message = "실패 : 유저 아이디가 중복 됨")
    })
    @GetMapping("/id-check/{id}")
    public ResponseEntity<?> idCheck(@PathVariable String id) {
        return success(new BoolResponse(signUpService.idCheck(id)));
    }

    /**
     * 인증번호 보내기
     * 이메일은 @mju.ac.kr 다 받아 올 것
     */
    @ApiOperation(value="회원 가입 검증 : 인증 메일 전송")
    @ApiResponses({
            @ApiResponse(code = 200, message = "인증 메일 전송 성공"),
            @ApiResponse(code = 400, message = "인증 메일 전송 실패")
    })
    @PostMapping("/auth/{userEmail}")
    public ResponseEntity<?> authMailSend(@PathVariable String userEmail) {
        String authToken = signUpService.setAuthToken(userEmail);
        emailService.sendMail(userEmail, authToken);
        log.info("Success : email send # address = {}, token = {}", userEmail, authToken);
        return success(new EmailResponse(userEmail, authToken));
    }

    /**
     * 인증번호 확인
     */
    @ApiOperation(value="회원 가입 검증 : 인증 번호 체크")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공 : 전송된 인증 번호와 동일"),
            @ApiResponse(code = 400, message = "실패 : 전송된 인증 번호와 다름")
    })
    @GetMapping("/auth/{userEmail}/{authToken}")
    public ResponseEntity<?> authTokenCheck(@PathVariable String userEmail, @PathVariable String authToken) {
        log.info("email = {}, authToken = {}", userEmail, authToken);
        log.info("authTokenList : {}", signUpService.getAuthTokenStorage());
        if (!signUpService.authTokenCheck(userEmail, authToken)) {
            log.error("Error : authToken mismatched");
            return badRequest(new ErrorResponse(ErrorCode.AUTH_TOKEN_ERROR, "authToken mismatched"));
        }
        log.info("Success : authToken matched");
        return success(new EmailResponse(userEmail, authToken));
    }


    /**
     *  예외 처리
     */
    // 비밀번호 불일치, 중복 이메일
    @ExceptionHandler(SignUpException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> signUpExHandle(SignUpException e) {
        log.error("[exceptionHandle] ex", e);
        return badRequest(new ErrorResponse(ErrorCode.SIGN_UP_ERROR, e.getMessage()));
    }


}
