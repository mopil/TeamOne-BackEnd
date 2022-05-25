package com.mjuteam2.TeamOne.member.domain;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.rating.domain.Rating;
import com.mjuteam2.TeamOne.util.domain.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity @Getter
public class MemberBoard extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_board_id")
    private Long id;

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
