package com.mjuteam2.TeamOne.caution;

import com.mjuteam2.TeamOne.member.Member;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
public class CautionList {

    @Id @GeneratedValue
    @Column(name = "caution_id")
    private Long id;

    @CreationTimestamp
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
