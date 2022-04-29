package com.mjuteam2.TeamOne.rating.service;

import com.mjuteam2.TeamOne.badge.repository.BadgeRepository;
import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.exception.BoardException;
import com.mjuteam2.TeamOne.borad.repository.BoardRepository;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.domain.MemberBoard;
import com.mjuteam2.TeamOne.member.exception.MemberException;
import com.mjuteam2.TeamOne.member.repository.MemberBoardRepository;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import com.mjuteam2.TeamOne.rating.domain.Rating;
import com.mjuteam2.TeamOne.rating.dto.RatingForm;
import com.mjuteam2.TeamOne.rating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RatingService {

    private final RatingRepository ratingRepository;
    private final BadgeRepository badgeRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final MemberBoardRepository memberBoardRepository;

    @Transactional
    public void save(Long boardId, Long memberId, RatingForm form) {
        // 해당 회원 조회
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new MemberException("멤버 조회 오류."));

        // 해당 회원을 평가하는 게시글 조회
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new BoardException("게시글 조회 오류."));

        // 멤버의 레이팅 추가 작업 (평점과 뱃지)
        findMember.addRating(form.getStar(), form.getBadge());

        // 멤버-보드 객체 생성
        MemberBoard memberBoard = new MemberBoard(findMember, findBoard);

        // 디비에 뱃지 저장
        badgeRepository.save(form.getBadge());

        // 디비에 평가 저장
        ratingRepository.save(new Rating(form.getStar(), memberBoard));

        // 디비에 멤버보드 저장
        memberBoardRepository.save(memberBoard);
    }

}
