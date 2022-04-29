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
    public void updateNickname(Member loginMember, String newNickname) {
        loginMember.updateNickname(newNickname);
    }

    // 비밀번호 변경
    public void updatePassword(Member loginMember, String newPassword) {
        loginMember.updatePassword(EncryptManager.hash(newPassword));
    }

    // 자기소개 변경
    public void updateIntroduce(Member loginMember, String newIntroduce) {
        loginMember.updateIntroduce(newIntroduce);
    }

    // 포인트 변경
    public void addPoint(Member loginMember, int amount) {
        loginMember.addPoint(amount);
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
