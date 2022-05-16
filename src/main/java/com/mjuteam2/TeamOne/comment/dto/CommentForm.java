package com.mjuteam2.TeamOne.comment.dto;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.comment.domain.Comment;
import com.mjuteam2.TeamOne.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentForm {

    @NotEmpty
    private String content;

    public Comment toEntity(Member writer, Board board) {
        return Comment.builder()
                .content(content)
                .board(board)
                .writer(writer)
                .build();
    }
}
