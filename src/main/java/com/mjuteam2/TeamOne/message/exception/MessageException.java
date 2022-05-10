package com.mjuteam2.TeamOne.message.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MessageException extends RuntimeException {
    public MessageException(String message) {super(message);}
}
