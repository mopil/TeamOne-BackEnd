package com.mjuteam2.TeamOne.caution.dto;

import com.mjuteam2.TeamOne.member.dto.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CautionResponse {
    private Long cautionId;
    private MemberResponse requestMember;
    private MemberResponse cautionedMember;
}
