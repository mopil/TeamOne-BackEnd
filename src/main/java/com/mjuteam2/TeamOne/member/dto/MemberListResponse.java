package com.mjuteam2.TeamOne.member.dto;

import com.mjuteam2.TeamOne.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class MemberListResponse {

    private List<Member> member;
    private String sessionId;

}
