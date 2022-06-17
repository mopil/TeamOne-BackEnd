package com.mjuteam2.TeamOne.member.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter @Embeddable
public class MemberValue {

    private double totalStar; // 기여도 평점 (감소되는것)
    private int ratingCount; // 평가를 받은 횟수
    private double viewStar; // 사람들에게 보여지는 평점 (소수점)

    private double point; // RPG 점수 (계속 쌓는것)


    /**
     * 비즈니스 로직
     */
    public void addRating(double star) {
        this.totalStar += star;
        this.ratingCount += 1;
        this.viewStar = (double)this.totalStar / this.ratingCount;
        addPoint(star * 5);
    }

    // 포인트 추가
    public void addPoint(double amount) {
        this.point += amount;
    }

    public MemberValue() {

    }
}
