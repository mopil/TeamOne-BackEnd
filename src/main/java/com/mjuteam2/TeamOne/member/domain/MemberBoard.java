package com.mjuteam2.TeamOne.member.domain;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.rating.domain.Rating;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Getter
public class MemberBoard {

    @Id @GeneratedValue
    @Column(name = "member_board_id")
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "memberBoard")
    private List<Rating> ratings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public MemberBoard(Member member, Board board) {
        this.member = member;
        this.board = board;
    }

    protected MemberBoard() {

    }
}
