package com.mjuteam2.TeamOne.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 중복 이메일, 비밀번호 확인, 인증 메일 보내는거 관련 예외 총괄
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SignUpException extends RuntimeException{
    public SignUpException(String message) {
        super(message);
    }
}
