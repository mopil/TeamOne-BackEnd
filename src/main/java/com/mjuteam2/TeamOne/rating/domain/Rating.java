package com.mjuteam2.TeamOne.rating.domain;

import com.mjuteam2.TeamOne.member.domain.MemberBoard;
import lombok.Getter;

import javax.persistence.*;

@Entity @Getter
public class Rating {

    @Id @GeneratedValue
    @Column(name = "rating_id")
    private Long id;

    // 소수점도 평가 가능 ex) 4.5
    private double star;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_board_id")
    private MemberBoard memberBoard;

    public Rating(double star, MemberBoard memberBoard) {
        this.star = star;
        this.memberBoard = memberBoard;
    }

    protected Rating() {

    }
}
