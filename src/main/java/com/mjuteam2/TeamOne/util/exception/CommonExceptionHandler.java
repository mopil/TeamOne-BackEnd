package com.mjuteam2.TeamOne.util.exception;

import com.mjuteam2.TeamOne.util.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.LoginException;

import static com.mjuteam2.TeamOne.util.dto.RestResponse.badRequest;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> otherExHandle(Exception e) {
        log.error("예외처리 하지 않은 예외 발생 : 핸들러 작동");
        return badRequest(new ErrorResponse(ErrorCode.COMMON_ERROR, e.getMessage()));
    }

    // 로그인 관련 예외
    @ExceptionHandler(LoginException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> loginExHandle(LoginException e) {
        log.error("로그인 예외 발생 : 핸들러 작동");
        return badRequest(new ErrorResponse(ErrorCode.LOGIN_ERROR, "인증 거부 : 로그인 안 됨."));
    }

}
