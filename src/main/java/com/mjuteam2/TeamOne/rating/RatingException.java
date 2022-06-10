package com.mjuteam2.TeamOne.rating;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RatingException extends RuntimeException{
    public RatingException(String message) {
        super(message);
    }
}
