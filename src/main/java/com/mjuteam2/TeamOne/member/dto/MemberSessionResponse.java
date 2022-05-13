package com.mjuteam2.TeamOne.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberSessionResponse {
    private MemberResponse member;
    private String sessionId;

}
