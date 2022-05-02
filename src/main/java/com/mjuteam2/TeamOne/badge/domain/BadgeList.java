package com.mjuteam2.TeamOne.badge.domain;

import com.mjuteam2.TeamOne.badge.dto.BadgeResponse;

import java.util.ArrayList;
import java.util.List;

// 해당 뱃지를 받으면 RPG용 포인트를 증가시킴(Member 엔티티의 point 칼럼)
public class BadgeList {
    
    // 포인트 3점
    public static final String FAST_RESPONSE = "연락을 잘 해요";
    public static final String GOOD_AT_PPT = "PPT를 잘 만들어요";
    public static final String GOOD_AT_REPORT = "보고서를 잘 써요";
    public static final String GOOD_AT_SEARCHING = "자료조사를 잘 해요";
    public static final String KINDNESS = "친절해요";
    public static final String RESPONSIBILITY = "책임감 있어요";
    public static final String TIME_PROMISE = "시간약속을 잘 지켜요";

    // 포인트 5점
    public static final String GOOD_AT_PRESENTATION = "발표를 잘해요";
    public static final String IDEA_BANK = "아이디어뱅크";
    public static final String LEADERSHIP = "리더십 있어요";
    
    // 포인트 10점
    public static final String HARD_CARRY = "하드캐리";

    
    // 존재하는 모든 종류 뱃지를 DTO로 만들어서 반환
    public static List<BadgeResponse> makeList() {
        List<BadgeResponse> result = new ArrayList<>();
        // 포인트 3점
        result.add(new BadgeResponse(FAST_RESPONSE, 3));
        result.add(new BadgeResponse(GOOD_AT_PPT, 3));
        result.add(new BadgeResponse(GOOD_AT_REPORT, 3));
        result.add(new BadgeResponse(GOOD_AT_SEARCHING, 3));
        result.add(new BadgeResponse(KINDNESS, 3));
        result.add(new BadgeResponse(RESPONSIBILITY, 3));
        result.add(new BadgeResponse(TIME_PROMISE, 3));

        // 포인트 5점
        result.add(new BadgeResponse(GOOD_AT_PRESENTATION, 5));
        result.add(new BadgeResponse(IDEA_BANK, 5));
        result.add(new BadgeResponse(LEADERSHIP, 5));

        // 포인트 10점
        result.add(new BadgeResponse(HARD_CARRY, 10));
        return result;
    }
}
