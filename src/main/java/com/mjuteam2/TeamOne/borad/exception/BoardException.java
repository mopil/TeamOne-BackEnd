package com.mjuteam2.TeamOne.borad.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BoardException extends RuntimeException {
    public BoardException(String message) {
        super(message);
    }
}
