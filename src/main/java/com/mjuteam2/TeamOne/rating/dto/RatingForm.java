package com.mjuteam2.TeamOne.rating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RatingForm {

    // 평가하는 그룹 Id
    private Long memberBoardId;

    // 평가하고자 하는 사람 멤버 ID
    private Long ratingMemberId;

    // 평가 받는 사람 맴버 ID
    private Long ratedMemberId;

    // 평점 소수점도 평가가능 ex) 4.5
    private double star;

    private int badge;
//    // 뱃지(키워드)
//    private List<Badge> badgeList;

}
