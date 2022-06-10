package com.mjuteam2.TeamOne.rating.service;

import com.mjuteam2.TeamOne.badge.repository.BadgeRepository;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.domain.MemberBoard;
import com.mjuteam2.TeamOne.member.exception.MemberBoardException;
import com.mjuteam2.TeamOne.member.exception.MemberException;
import com.mjuteam2.TeamOne.member.repository.MemberBoardRepository;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import com.mjuteam2.TeamOne.message.dto.MessageRoomListResponse;
import com.mjuteam2.TeamOne.message.dto.MessageRoomResponse;
import com.mjuteam2.TeamOne.rating.RatingException;
import com.mjuteam2.TeamOne.rating.domain.Rating;
import com.mjuteam2.TeamOne.rating.dto.RatingForm;
import com.mjuteam2.TeamOne.rating.dto.RatingListResponse;
import com.mjuteam2.TeamOne.rating.dto.RatingResponse;
import com.mjuteam2.TeamOne.rating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RatingService {

    private final RatingRepository ratingRepository;
    private final BadgeRepository badgeRepository;
    private final MemberRepository memberRepository;
    private final MemberBoardRepository memberBoardRepository;


    /**
     * 평가 생성
     */
    @Transactional
    public void ratingCreate(RatingForm form) {

//        // 기존에 평가가 되어있는지 확인
//        Optional<Rating> ratingCheck = ratingRepository.findRating(form.getMemberBoardId(), form.getMemberId(), form.getRatingMemberId());
//        if (ratingCheck.isPresent()) {
//            throw new RatingException("이미 평가가 존재합니다.");
//        }

        // 평가 하는 회원 조회
        Member ratingMember = memberRepository.findById(form.getRatingMemberId()).orElseThrow(() -> new MemberException("멤버 조회 오류."));

        // 평가 받는 회원 조회
        Member ratedMember = memberRepository.findById(form.getRatedMemberId()).orElseThrow(() -> new MemberException("맴버 조회 오류 (평가 받는 사람)."));

        // 맴버 보드 조회
        MemberBoard memberBoard = memberBoardRepository.findById(form.getMemberBoardId()).orElseThrow(() -> new MemberBoardException("그룹 조회 오류."));

        // 평가 생성
        //Rating rating = new Rating(ratingMember, ratedMember, memberBoard, form.getStar(), form.getBadgeList());
        Rating rating = Rating.builder()
                .ratingMember(ratingMember)
                .ratedMember(ratedMember)
                .memberBoard(memberBoard)
                .star(form.getStar())
                .build();

        // 평가 받는 멤버의 레이팅 추가 작업 (평점과 뱃지) -> 맴버에 업데이트
        //ratedMember.addRating(form.getStar(), form.getBadgeList());
        ratedMember.addRating(form.getStar());

        // 디비에 평가 내역 저장
        ratingRepository.save(rating);

    }

    /**
     * 평가 조회 (내가 받은 평가)
     */
    public RatingListResponse findRating(Long memberId) {
        List<Rating> ratingList = ratingRepository.findAllByRatingMemberId(memberId);
        List<RatingResponse> result = new ArrayList<>();
        ratingList.forEach(rating -> result.add(rating.toResponse()));
        return new RatingListResponse(result);
    }

    /**
     * 평가 조회 (내가 한 평가)
     */
    public RatingListResponse findRated(Long memberId) {
        List<Rating> ratingList = ratingRepository.findAllRatingByRatedMember3(memberId);
        List<RatingResponse> result = new ArrayList<>();
        ratingList.forEach(rating -> result.add(rating.toResponse()));
        return new RatingListResponse(result);
    }



}
