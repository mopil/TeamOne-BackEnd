package com.mjuteam2.TeamOne.rating.dto;

import com.mjuteam2.TeamOne.badge.domain.Badge;
import lombok.Data;

@Data
public class RatingForm {

    // 평가하고자 하는 사람 멤버 ID
    private Long memberId;

    // 평점 소수점도 평가가능 ex) 4.5
    private double star;

    // 뱃지(키워드)
    private Badge badge;

}
