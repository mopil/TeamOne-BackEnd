package com.mjuteam2.TeamOne.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)

public class MemberBoardException extends RuntimeException {
    public MemberBoardException(String message) {
        super(message);
    }
}
