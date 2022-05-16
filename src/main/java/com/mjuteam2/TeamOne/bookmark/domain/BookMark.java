package com.mjuteam2.TeamOne.bookmark.domain;

import com.mjuteam2.TeamOne.bookmark.dto.BookMarkResponse;
import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.util.domain.BaseTimeEntity;
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
public class BookMark extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;


    @Builder
    public BookMark(Member member, Board board) {
        this.member = member;
        this.board = board;
    }

    public BookMarkResponse toResponse() {
        return BookMarkResponse.builder()
                .bookMarkId(id)
                .writer(member.toResponse())
                .board(board.toResponse(board.getBoardType()))
                .createdDate(getCreatedDate())
                .build();
    }
}
