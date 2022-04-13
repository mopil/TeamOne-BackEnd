package com.mjuteam2.TeamOne.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FindFormException extends RuntimeException {
    public FindFormException(String message) {
        super(message);
    }
}

