package com.mjuteam2.TeamOne.comment.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.comment.dto.CommentResponse;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.util.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonBackReference
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    @Builder
    public Comment(Member writer, String content, Board board) {
        this.member = writer;
        this.content = content;
        this.board = board;
    }

    public void update(@NotEmpty String comment) {
        this.content = comment;
    }

    public CommentResponse toResponse() {
        return CommentResponse.builder()
                .content(content)
                .writer(member.toResponse())
                .boardId(board.getId())
                .commentId(id)
                .createdDate(getCreatedDate())
                .updatedDate(getUpdatedDate())
                .build();
    }
}
