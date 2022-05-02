package com.mjuteam2.TeamOne.badge.controller;

import com.mjuteam2.TeamOne.badge.domain.BadgeList;
import com.mjuteam2.TeamOne.badge.service.BadgeService;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static com.mjuteam2.TeamOne.util.dto.RestResponse.success;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/badges")
public class BadgeController {

    private final BadgeService badgeService;
    private final MemberRepository memberRepository; // 테스트용
    
    // 뱃지를 수정,삭제할 일이 있나...?? 없을것 같아서 안 만들긴 했는데... 회의 필요

    // 존재하는 모든 종류의 뱃지 조회
    @GetMapping("/all")
    public ResponseEntity<?> showAllBadges() {
        return success(badgeService.showAllBadges());
    }

    // 특정 사용자가 가지고 있는 뱃지 전체 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<?> findAllBadges(@PathVariable Long memberId) {
        return success(badgeService.findAllBadges(memberId));
    }

    // 뱃지 추가 : 테스트용
    @PostMapping("/{memberId}")
    @Transactional
    public ResponseEntity<?> addBadge(@PathVariable Long memberId) {
        badgeService.addBadge(memberId, BadgeList.HARD_CARRY);
        badgeService.addBadge(memberId, BadgeList.FAST_RESPONSE);
        badgeService.addBadge(memberId, BadgeList.GOOD_AT_PRESENTATION);
        return success(memberRepository.findById(memberId));
    }
}
