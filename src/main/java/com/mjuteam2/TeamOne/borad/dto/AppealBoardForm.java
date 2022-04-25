package com.mjuteam2.TeamOne.borad.dto;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.domain.BoardStatus;
import com.mjuteam2.TeamOne.borad.domain.BoardType;
import com.mjuteam2.TeamOne.member.domain.Member;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AppealBoardForm implements BoardForm {
    @NotEmpty
    private String title;

    @NotEmpty
    private String classTitle;

    @NotEmpty
    private String classDate;

    @NotEmpty
    private String content;

    @Override
    public Board toBoard(Member writer, BoardType boardType) {
        return Board.builder()
                .writer(writer)
                .title(this.title)
                .content(this.content)
                .boardType(boardType)
                .boardStatus(BoardStatus.DEFAULT)
                .classTitle(this.classTitle)
                .classDate(this.classDate)
                .build();
    }

}
