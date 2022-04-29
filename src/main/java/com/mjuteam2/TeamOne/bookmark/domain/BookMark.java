package com.mjuteam2.TeamOne.bookmark.domain;

import com.mjuteam2.TeamOne.bookmark.dto.BookMarkResponse;
import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookMark {

    @Id @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;


    @Builder
    public BookMark(Member member, Board board, LocalDateTime createdAt) {
        this.member = member;
        this.board = board;
        this.createdAt = createdAt;
    }

    public BookMarkResponse toResponse() {
        return BookMarkResponse.builder()
                .boardId(board.getId())
                .memberId(member.getId())
                .bookMarkId(id)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
