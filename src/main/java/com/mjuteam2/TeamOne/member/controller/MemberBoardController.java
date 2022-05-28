package com.mjuteam2.TeamOne.member.controller;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.domain.MemberBoard;
import com.mjuteam2.TeamOne.member.dto.MemberBoardResponse;
import com.mjuteam2.TeamOne.member.service.MemberBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mjuteam2.TeamOne.util.dto.RestResponse.success;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member-board")
public class MemberBoardController {

    private final MemberBoardService memberBoardService;

    @PostMapping("")
    public ResponseEntity<?> createMemberBoard(Long memberId, Long boardId) {
        MemberBoardResponse memberBoard = memberBoardService.createMemberBoard(boardId, memberId);
        return success(memberBoard);
    }

    @PostMapping("/ok")
    public ResponseEntity<?> approvalMemberBoard(Long memberBoardId) {
        MemberBoardResponse approvalMemberBoards = memberBoardService.approvalMemberBoard(memberBoardId);
        return success(approvalMemberBoards);
    }

    @PostMapping("/no")
    public ResponseEntity<?> noApprovalMemberBoard(Long memberBoardId) {
        MemberBoardResponse approvalMemberBoards = memberBoardService.noApprovalMemberBoard(memberBoardId);
        return success(approvalMemberBoards);
    }
}

