package com.mjuteam2.TeamOne.badge.service;

import com.mjuteam2.TeamOne.badge.domain.Badge;
import com.mjuteam2.TeamOne.badge.domain.BadgeList;
import com.mjuteam2.TeamOne.badge.dto.BadgeResponse;
import com.mjuteam2.TeamOne.badge.repository.BadgeRepository;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.exception.MemberException;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final MemberRepository memberRepository;

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new MemberException("해당 사용자 조회 오류."));
    }

    // 사용자의 모든 뱃지 조회
    public List<BadgeResponse> findAllBadges(Long memberId) {
        Member findMember = getMemberById(memberId);
        List<BadgeResponse> result = new ArrayList<>();
        badgeRepository.findAllByMember(findMember).forEach(badge -> result.add(badge.toResponse()));
        return result;
    }

    // 존재하는 모든 종류의 뱃지 조회
    public List<BadgeResponse> showAllBadges() {
        return BadgeList.makeList();
    }


    // 뱃지 테스트용 메소드
    @Transactional
    public boolean addBadge(Long memberId, String keyword) {
        // 멤버 조회
        Member findMember = getMemberById(memberId);

        Badge badge = new Badge(findMember, keyword);
        
        // 멤버의 badges에 badge 추가
        findMember.addBadge(badge);
        
        // 디비에도 저장
        badgeRepository.save(badge);
        return true;
    }

}
