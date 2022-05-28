package com.mjuteam2.TeamOne.member.service;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.domain.BoardStatus;
import com.mjuteam2.TeamOne.borad.exception.BoardException;
import com.mjuteam2.TeamOne.borad.repository.BoardRepository;
import com.mjuteam2.TeamOne.member.domain.Admission;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.domain.MemberBoard;
import com.mjuteam2.TeamOne.member.dto.MemberBoardResponse;
import com.mjuteam2.TeamOne.member.exception.MemberBoardException;
import com.mjuteam2.TeamOne.member.exception.MemberException;
import com.mjuteam2.TeamOne.member.repository.MemberBoardRepository;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberBoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final MemberBoardRepository memberBoardRepository;

    /**
     * memberBoard Create -> WAIT
     */
    public MemberBoardResponse createMemberBoard(Long joinBoardId, Long joinMemberId) {
        Member joinMember = memberRepository.findById(joinMemberId).orElseThrow(() -> new MemberException("맴버가 존재하지 않습니다."));
        Board joinBoard = boardRepository.findById(joinBoardId).orElseThrow(() -> new BoardException("글이 존재하지 않습니다."));

        MemberBoard memberBoard = new MemberBoard(joinMember, joinBoard, Admission.WAIT);
        MemberBoard saveMemberBoard = memberBoardRepository.save(memberBoard);
        return saveMemberBoard.toResponse();
    }

    /**
     * Approval MemberBoard
     */
    public MemberBoardResponse approvalMemberBoard(Long memberBoardId) {
        MemberBoard findMemberBoard = memberBoardRepository.findById(memberBoardId).orElseThrow(() -> new MemberBoardException("dasd"));
        BoardStatus boardStatus = findMemberBoard.getBoard().getBoardStatus();
        if (boardStatus.equals(BoardStatus.DEFAULT)) {
            findMemberBoard.changeAdmission(Admission.APPROVAL);
            return findMemberBoard.toResponse();
        } else {
            throw new MemberBoardException("보드 상태 오류. 모집 중이어야 함");
        }
    }

    /**
     * No_Approval MemberBoard
     */
    public MemberBoardResponse noApprovalMemberBoard(Long memberBoardId) {
        MemberBoard findMemberBoard = memberBoardRepository.findById(memberBoardId).orElseThrow(() -> new MemberException("dasd"));
        BoardStatus boardStatus = findMemberBoard.getBoard().getBoardStatus();
        if (boardStatus.equals(BoardStatus.DEFAULT)) {
            findMemberBoard.changeAdmission(Admission.NO_APPROVAL);
            return findMemberBoard.toResponse();
        } else {
            throw new MemberBoardException("보드 상태 오류. 모집 중이어야 함");
        }
    }
}
