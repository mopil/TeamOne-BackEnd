package com.mjuteam2.TeamOne.comment.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.comment.dto.CommentResponse;
import com.mjuteam2.TeamOne.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

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
                .createdAt(createdAt)
                .content(content)
                .memberId(member.getId())
                .BoardId(board.getId())
                .commentId(id)
                .build();
    }
}
