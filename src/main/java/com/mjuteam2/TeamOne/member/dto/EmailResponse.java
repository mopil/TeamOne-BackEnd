package com.mjuteam2.TeamOne.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class EmailResponse {
    public enum EmailProcessResult {
        SUCCESS, FAIL
    }
    String userEmail;
    String authToken;
    EmailProcessResult emailProcessResult;
    String message;
}
