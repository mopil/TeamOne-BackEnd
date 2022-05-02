package com.mjuteam2.TeamOne.badge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjuteam2.TeamOne.badge.dto.BadgeResponse;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.rating.domain.Rating;
import lombok.Getter;

import javax.persistence.*;

@Entity @Getter
public class Badge {

    @Id @GeneratedValue
    @Column(name = "badge_id")
    private Long id;

    private String keyword;
    
    // 해당 뱃지를 받으면 몇 포인트를 줄 것인지
    private int point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_id")
    private Rating rating;

    protected Badge() {

    }

    public Badge(Member member, String keyword) {
        this.member = member;
        this.keyword = keyword;
    }

    public BadgeResponse toResponse() {
        return new BadgeResponse(this.keyword, this.point);
    }
}
