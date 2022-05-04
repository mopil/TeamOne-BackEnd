package com.mjuteam2.TeamOne.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MemberResponse {
    private Long memberId;
    private String userId;
    private String password;
    private String email;
    private String userName;
    private String department;
    private String schoolId;
    private String phoneNumber;
    private String nickname;
    private String introduce;
}
