package com.mjuteam2.TeamOne.member.service;

import com.mjuteam2.TeamOne.member.config.EncryptManager;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.exception.MemberException;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final SignInService signInService;

    /**
     * 회원 하나 조회
     */
    @Transactional(readOnly = true)
    public Member findByUserId(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() ->  new MemberException("해당 회원이 없습니다."));
    }

    /**
     * 회원 정보 수정
     */
    // 닉네임 변경
    public Member updateNickname(Long id, String newNickname) {
        memberRepository.updateNickname(id, newNickname);
        return findByUserId(id);
    }

    // 비밀번호 변경
    public Member updatePassword(Long id, String newPassword) {
        memberRepository.updatePassword(id, EncryptManager.hash(newPassword));
        return findByUserId(id);
    }

    // 평점(star) 변경
    public Member updateStar(Long id, double newStar) {
        memberRepository.updateStar(id, newStar);
        return findByUserId(id);
    }

    // 포인트 변경 (랭킹 포인트)
    public Member updatePoint(Long id, int newPoint) {
        memberRepository.updatePoint(id, newPoint);
        return findByUserId(id);
    }

    // 자기소개 변경
    public Member updateIntroduce(Long id, String newIntroduce) {
        memberRepository.updateIntroduce(id, newIntroduce);
        return findByUserId(id);
    }


    /**
     * 회원 탈퇴
     */
    public boolean deleteMember(Long id, HttpServletRequest request) {
        memberRepository.deleteById(id);
        signInService.logout(request);
        return true;
    }
}
