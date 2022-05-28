package com.mjuteam2.TeamOne.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MemberBoardListResponse {

    private List<MemberBoardResponse> memberBoardResponseList;
}
