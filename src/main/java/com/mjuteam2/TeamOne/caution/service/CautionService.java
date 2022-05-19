package com.mjuteam2.TeamOne.caution.service;

import com.mjuteam2.TeamOne.caution.domain.Caution;
import com.mjuteam2.TeamOne.caution.dto.CautionListResponse;
import com.mjuteam2.TeamOne.caution.dto.CautionResponse;
import com.mjuteam2.TeamOne.caution.exception.CautionException;
import com.mjuteam2.TeamOne.caution.repository.CautionRepository;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.exception.MemberException;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CautionService {

    private final CautionRepository cautionRepository;
    private final MemberRepository memberRepository;

    // 멤버 조회 편의 메서드
    private Member findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberException("해당 사용자 조회 오류."));
    }

    // 차단하기
    public CautionResponse setCaution(Long requestMemberId, Long targetMemberId) {
        // 유의 설정 중복 체크 (이미 설정된 유의가 있는지)
        Optional<Caution> duple = cautionRepository.duplicateCheck(requestMemberId, targetMemberId);
        if (duple.isPresent()) throw new CautionException("이미 유의 설정한 대상입니다.");

        Member requestMember = findMemberById(requestMemberId);
        Member targetMember = findMemberById(targetMemberId);
        Caution caution = Caution.builder()
                .requestMember(requestMember)
                .cautionedMember(targetMember)
                .build();
        log.info("유의 생성 // 차단을 요청한 사용자 = {}, 차단된 사용자 = {}", caution.getRequestMember(), caution.getTargetMember());
        cautionRepository.save(caution);
        return caution.toResponse();
    }

    // 차단목록 조회 (사용자 기준)
    @Transactional(readOnly = true)
    public CautionListResponse findAll(Long requestMemberId) {
        List<Caution> findCautions = cautionRepository.findAllByRequestMemberId(requestMemberId);
        List<CautionResponse> result = new ArrayList<>();
        findCautions.forEach(caution -> result.add(caution.toResponse()));
        return new CautionListResponse(result);
    }

    // 차단해제(차단삭제)
    public void removeCaution(Long cautionedMemberId) {
        Caution caution = cautionRepository.findByCautionedMemberId(cautionedMemberId).orElseThrow(() -> new CautionException("유의 조회 실패."));
        log.info("조회된 유의 // 차단을 요청한 사용자 = {}, 차단된 사용자 = {}", caution.getRequestMember(), caution.getTargetMember());
        cautionRepository.delete(caution);
    }

    // 사용자가 가지고 있는 차단목록 전체 삭제
    public void removeAllCaution(Long memberId) {
        List<Caution> findCautions = cautionRepository.findAllByRequestMemberId(memberId);
        cautionRepository.deleteAll(findCautions);
    }
}
