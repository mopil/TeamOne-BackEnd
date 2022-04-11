package com.mjuteam2.TeamOne.borad.dto;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.domain.BoardStatus;
import com.mjuteam2.TeamOne.borad.domain.BoardType;
import com.mjuteam2.TeamOne.member.domain.Member;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
public class BoardForm {
    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private String classTitle;
    private String classDate;
    private LocalDateTime deadLine;
    private int memberCount;

    public Board toBoard(Member writer, BoardType boardType) {
         return Board.builder()
                 .writer(writer)
                .title(this.title)
                .content(this.content)
                .boardType(boardType)
                .boardStatus(BoardStatus.DEFAULT)
                .classTitle(this.classTitle)
                .classDate(this.classDate)
                .deadline(this.deadLine)
                .memberCount(this.memberCount)
                .build();
    }


}
