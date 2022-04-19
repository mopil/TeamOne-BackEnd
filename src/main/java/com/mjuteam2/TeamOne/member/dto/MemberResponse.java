package com.mjuteam2.TeamOne.member.dto;

import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.domain.MemberType;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
public class MemberResponse {

    private Long id; // 멤버 PK 아이디
    private String sessionId;

}
