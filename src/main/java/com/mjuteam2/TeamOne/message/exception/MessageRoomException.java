package com.mjuteam2.TeamOne.message.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MessageRoomException extends RuntimeException {
    public MessageRoomException(String message) {super(message);}
}
