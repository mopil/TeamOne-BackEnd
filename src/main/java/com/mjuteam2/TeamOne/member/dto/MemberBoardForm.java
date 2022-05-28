package com.mjuteam2.TeamOne.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class MemberBoardForm {
    @NotEmpty
    private Long boardId;
    @NotEmpty
    private Long memberId;
}
