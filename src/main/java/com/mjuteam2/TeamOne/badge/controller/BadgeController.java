package com.mjuteam2.TeamOne.badge.controller;

import com.mjuteam2.TeamOne.badge.service.BadgeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mjuteam2.TeamOne.util.dto.RestResponse.success;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/badges")
public class BadgeController {

    private final BadgeService badgeService;

    // 특정 사용자가 가지고 있는 뱃지 전체 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<?> findAllBadges(@PathVariable Long memberId) {
        return success(badgeService.findAllBadges(memberId));
    }
}
