package com.mjuteam2.TeamOne.member.service;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.domain.BoardStatus;
import com.mjuteam2.TeamOne.borad.exception.BoardException;
import com.mjuteam2.TeamOne.borad.repository.BoardRepository;
import com.mjuteam2.TeamOne.member.domain.Admission;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.domain.MemberBoard;
import com.mjuteam2.TeamOne.member.dto.MemberBoardListResponse;
import com.mjuteam2.TeamOne.member.dto.MemberBoardResponse;
import com.mjuteam2.TeamOne.member.exception.MemberBoardException;
import com.mjuteam2.TeamOne.member.exception.MemberException;
import com.mjuteam2.TeamOne.member.repository.MemberBoardRepository;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
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

        int memberCount = joinBoard.getMemberCount();
        int currentMemberCount = joinBoard.getCurrentMemberCount();

        if(currentMemberCount >= memberCount){
            throw new BoardException("모집 인원이 다 찼습니다.");
        }

        MemberBoard memberBoard = new MemberBoard(joinMember, joinBoard, Admission.WAIT);
        Optional<MemberBoard> exist = memberBoardRepository.findByMemberIdAndBoardId(joinMemberId, joinBoardId);

        if (exist.isEmpty()) {

            if (joinBoard.getBoardStatus() != BoardStatus.OK) {

                MemberBoard saveMemberBoard = memberBoardRepository.save(memberBoard);
                return saveMemberBoard.toResponse();

            } else {
                throw new BoardException("모집 종료된 게시글 입니다.");
            }
        } else {
            throw new MemberBoardException("이미 가입 신청을 했습니다.");
        }
    }

    /**
     * Approval MemberBoard
     */
    public MemberBoardResponse approvalMemberBoard(Long memberBoardId) {
        MemberBoard findMemberBoard = memberBoardRepository.findById(memberBoardId).orElseThrow(() -> new MemberBoardException("신청된 맴버를 조회할 수 없습니다."));
        BoardStatus boardStatus = findMemberBoard.getBoard().getBoardStatus();
        if (boardStatus.equals(BoardStatus.DEFAULT)) {
            findMemberBoard.changeAdmission(Admission.APPROVAL);

            // 참여 중인 맴버 증가
            findMemberBoard.getBoard().addCurrentMemberCount();

            return findMemberBoard.toResponse();
        } else {
            throw new MemberBoardException("보드 상태 오류. 모집 중이어야 함");
        }
    }

    /**
     * No_Approval MemberBoard
     */
    public MemberBoardResponse noApprovalMemberBoard(Long memberBoardId) {
        MemberBoard findMemberBoard = memberBoardRepository.findById(memberBoardId).orElseThrow(() -> new MemberException("신청된 맴버를 조회할 수 없습니다."));
        BoardStatus boardStatus = findMemberBoard.getBoard().getBoardStatus();
        if (boardStatus.equals(BoardStatus.DEFAULT)) {
            findMemberBoard.changeAdmission(Admission.NO_APPROVAL);
            return findMemberBoard.toResponse();
        } else {
            throw new MemberBoardException("보드 상태 오류. 모집 중이어야 함");
        }
    }

    /**
     * MemberBoard 조회 (Board 기준)
     */
    public MemberBoardListResponse findMemberBoardByBoard(Long boardId) {
        List<MemberBoard> memberBoardList = memberBoardRepository.findAllByBoardId(boardId);
        List<MemberBoardResponse> result = new ArrayList<>();
        memberBoardList.forEach(memberBoard -> result.add(memberBoard.toResponse()));
        return new MemberBoardListResponse(result);
    }

    /**
     * MemberBoard 조회 (Member 기준)
     */
    public MemberBoardListResponse findMemberBoardByMember(Long memberId) {
        List<MemberBoard> memberBoardList = memberBoardRepository.findAllByMemberId(memberId);
        List<MemberBoardResponse> result = new ArrayList<>();
        memberBoardList.forEach(memberBoard -> result.add(memberBoard.toResponse()));
        return new MemberBoardListResponse(result);
    }

    /**
     * MemberBoard 조회 (Approval)
     */
    public MemberBoardListResponse findMemberBoardByApproval(Long boardId) {
        List<MemberBoard> allByAdmission_approval = memberBoardRepository.findAllByAdmission_Approval(boardId, Admission.APPROVAL);
        List<MemberBoardResponse> result = new ArrayList<>();
        allByAdmission_approval.forEach(memberBoard -> result.add(memberBoard.toResponse()));
        return new MemberBoardListResponse(result);
    }

    /**
     * MemberBoard 조회 (No_Approval)
     */
    public MemberBoardListResponse findMemberBoardByNoApproval(Long boardId) {
        List<MemberBoard> allByAdmission_approval = memberBoardRepository.findAllByAdmission_Approval(boardId, Admission.NO_APPROVAL);
        List<MemberBoardResponse> result = new ArrayList<>();
        allByAdmission_approval.forEach(memberBoard -> result.add(memberBoard.toResponse()));
        return new MemberBoardListResponse(result);
    }

    /**
     * MemberBoard 조회 (Wait)
     */
    public MemberBoardListResponse findMemberBoardByWait(Long boardId) {
        List<MemberBoard> allByAdmission_approval = memberBoardRepository.findAllByAdmission_Approval(boardId, Admission.WAIT);
        List<MemberBoardResponse> result = new ArrayList<>();
        allByAdmission_approval.forEach(memberBoard -> result.add(memberBoard.toResponse()));
        return new MemberBoardListResponse(result);
    }

    /**
     * MemberBoard 조회 (Approval) memberId로 !
     */
    public MemberBoardListResponse findMemberBoardByApprovalWithoutMe(Long memberId) {
        List<MemberBoard> allByAdmission_approval = memberBoardRepository.findAllByAdmission_Approval_WithoutMe(memberId, Admission.APPROVAL);
        List<MemberBoardResponse> result = new ArrayList<>();
        allByAdmission_approval.forEach(memberBoard -> {
            result.add(memberBoard.toResponse());
        });
        return new MemberBoardListResponse(result);
    }

}
