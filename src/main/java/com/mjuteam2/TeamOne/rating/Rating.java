package com.mjuteam2.TeamOne.rating;

import com.mjuteam2.TeamOne.member.MemberBoard;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
public class Rating {

    @Id @GeneratedValue
    @Column(name = "rating_id")
    private Long id;

    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_board_id")
    private MemberBoard memberBoard;
}
