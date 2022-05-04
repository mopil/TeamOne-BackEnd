package com.mjuteam2.TeamOne.caution.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CautionException extends RuntimeException{
    public CautionException(String message) {
        super(message);
    }
}
