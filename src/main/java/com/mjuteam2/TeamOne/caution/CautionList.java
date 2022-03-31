package com.mjuteam2.TeamOne.caution;

import com.mjuteam2.TeamOne.member.Member;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Getter
public class CautionList {

    @Id @GeneratedValue
    @Column(name = "caution_id")
    private Long id;

    @CreationTimestamp
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
