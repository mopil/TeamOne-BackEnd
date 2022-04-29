package com.mjuteam2.TeamOne.member.domain;

import com.mjuteam2.TeamOne.badge.domain.Badge;
import lombok.Getter;

import javax.persistence.*;

@Entity @Getter
public class MemberValue {

    @Id @GeneratedValue
    @Column(name = "member_value_id")
    private Long id;

    private int totalStar; // 기여도 평점 (감소되는것)
    private int ratingCount; // 평가를 받은 횟수
    private double viewStar; // 사람들에게 보여지는 평점 (소수점)

    private int point; // RPG 점수 (계속 쌓는것)

    @OneToOne(mappedBy = "memberValue", cascade = CascadeType.ALL)
    private Member member;

    /**
     * 비즈니스 로직
     */
    // 평점, 뱃지 추가
    public void addRating(double star, Badge badge) {
        this.member.addBadge(badge);
        this.totalStar += star;
        this.ratingCount += 1;
        this.viewStar = (double)this.totalStar / this.ratingCount;
    }

    // 포인트 추가
    public void addPoint(int amount) {
        this.point += amount;
    }
}
