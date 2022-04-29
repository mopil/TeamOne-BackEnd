package com.mjuteam2.TeamOne.badge.service;

import com.mjuteam2.TeamOne.badge.domain.Badge;
import com.mjuteam2.TeamOne.badge.repository.BadgeRepository;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.exception.MemberException;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Badge> findAllBadges(Long memberId) {
        Member findMember = getMemberById(memberId);
        return badgeRepository.findAllByMember(findMember);
    }


    // 뱃지 테스트용 메소드
    @Transactional
    public boolean addBadge(Long memberId, Badge badge) {
        // 멤버 조회
        Member findMember = getMemberById(memberId);
        
        // 멤버의 badges에 badge 추가
        findMember.addBadge(badge);
        
        // 디비에도 저장
        badgeRepository.save(badge);
        return true;
    }

}
