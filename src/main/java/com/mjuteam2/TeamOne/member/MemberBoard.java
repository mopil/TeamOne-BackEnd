package com.mjuteam2.TeamOne.member;

import com.mjuteam2.TeamOne.borad.Board;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Getter
public class MemberBoard {

    @Id @GeneratedValue
    @Column(name = "member_board_id")
    private Long id;

    @CreationTimestamp
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}
