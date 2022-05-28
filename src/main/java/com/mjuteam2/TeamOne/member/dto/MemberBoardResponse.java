package com.mjuteam2.TeamOne.member.dto;

import com.mjuteam2.TeamOne.member.domain.Admission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MemberBoardResponse {
    private Long memberBoardId;
    private String Admission;
    private String createdDate;
}
