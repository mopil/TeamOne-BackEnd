package com.mjuteam2.TeamOne.member.controller;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.domain.MemberBoard;
import com.mjuteam2.TeamOne.member.dto.MemberBoardForm;
import com.mjuteam2.TeamOne.member.dto.MemberBoardListResponse;
import com.mjuteam2.TeamOne.member.dto.MemberBoardResponse;
import com.mjuteam2.TeamOne.member.service.MemberBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mjuteam2.TeamOne.util.dto.RestResponse.success;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member-board")
public class MemberBoardController {

    private final MemberBoardService memberBoardService;

    /**
     * MemberBoard 생성
     */

    @PostMapping("")
    public ResponseEntity<?> createMemberBoard(@RequestBody MemberBoardForm form) {
        MemberBoardResponse memberBoard = memberBoardService.createMemberBoard(form.getBoardId(), form.getMemberId());
        return success(memberBoard);
    }

    /**
     * MemberBoard 승인
     */

    @PostMapping("/{memberBoardId}")
    public ResponseEntity<?> approvalMemberBoard(@PathVariable Long memberBoardId) {
        MemberBoardResponse approvalMemberBoards = memberBoardService.approvalMemberBoard(memberBoardId);
        return success(approvalMemberBoards);
    }

    /**
     * MemberBoard 거부
     */

    @PostMapping("/no/{memberBoardId}")
    public ResponseEntity<?> noApprovalMemberBoard(@PathVariable Long memberBoardId) {
        MemberBoardResponse approvalMemberBoards = memberBoardService.noApprovalMemberBoard(memberBoardId);
        return success(approvalMemberBoards);
    }

    /**
     * MemberBoard 게시글 기준
     */

    @GetMapping("/board/{boardId}")
    public ResponseEntity<?> findMemberBoardByBoardId(@PathVariable Long boardId) {
        MemberBoardListResponse memberBoardByBoard = memberBoardService.findMemberBoardByBoard(boardId);
        return success(memberBoardByBoard);
    }

    /**
     * MemberBoard 유저 기준
     */

    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> findMemberBoardByMemberId(@PathVariable Long memberId) {
        MemberBoardListResponse memberBoardByBoard = memberBoardService.findMemberBoardByMember(memberId);
        return success(memberBoardByBoard);
    }

    /**
     * 승인 된 MemberBoard 조회 (게시글 기준)
     */

    @GetMapping("/ok/{boardId}")
    public ResponseEntity<?> findAllApproval(@PathVariable Long boardId) {
        MemberBoardListResponse memberBoardByApproval = memberBoardService.findMemberBoardByApproval(boardId);
        return success(memberBoardByApproval);
    }

    /**
     * 거부 된 MemberBoard 조회 (게시글 기준)
     */

    @GetMapping("/no/{boardId}")
    public ResponseEntity<?> findAllNoApproval(@PathVariable Long boardId) {
        MemberBoardListResponse memberBoardByApproval = memberBoardService.findMemberBoardByNoApproval(boardId);
        return success(memberBoardByApproval);
    }

    /**
     * 대기 중인 MemberBoard 조회 (게시글 기준)
     */

    @GetMapping("/wait/{boardId}")
    public ResponseEntity<?> findAllWait(@PathVariable Long boardId) {
        MemberBoardListResponse memberBoardByApproval = memberBoardService.findMemberBoardByWait(boardId);
        return success(memberBoardByApproval);
    }

    /**
     * 맴버 기준 승인된 그룹 조회
     */
    @GetMapping("/approval/member/{memberId}")
    public ResponseEntity<?> findAllApprovalMember(@PathVariable Long memberId) {
        MemberBoardListResponse memberBoardByApproval = memberBoardService.findMemberBoardByApprovalWithoutMe(memberId);
        return success(memberBoardByApproval);
    }

    /**
     * 게시판 기준 승인된 맴버 전부 조회 (본인 제외)
     */
    @PostMapping("/finish")
    public ResponseEntity<?> finishMemberList(@RequestBody MemberBoardForm form) {
        MemberBoardListResponse memberList = memberBoardService.finishMemberList(form.getMemberId(), form.getBoardId());
        return success(memberList);
    }
}

