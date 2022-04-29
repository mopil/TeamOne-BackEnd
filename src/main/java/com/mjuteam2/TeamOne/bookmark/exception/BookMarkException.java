package com.mjuteam2.TeamOne.bookmark.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BookMarkException extends RuntimeException{
    public BookMarkException(String message) {
        super(message);
    }
}
