package com.mjuteam2.TeamOne.member.controller;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.domain.MemberBoard;
import com.mjuteam2.TeamOne.member.dto.MemberBoardForm;
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

    @PostMapping("")
    public ResponseEntity<?> createMemberBoard(@RequestBody MemberBoardForm form) {
        MemberBoardResponse memberBoard = memberBoardService.createMemberBoard(form.getBoardId(), form.getMemberId());
        return success(memberBoard);
    }

    @PostMapping("/{memberBoardId}")
    public ResponseEntity<?> approvalMemberBoard(@PathVariable Long memberBoardId) {
        MemberBoardResponse approvalMemberBoards = memberBoardService.approvalMemberBoard(memberBoardId);
        return success(approvalMemberBoards);
    }

    @PostMapping("/no/{memberBoardId}")
    public ResponseEntity<?> noApprovalMemberBoard(@PathVariable Long memberBoardId) {
        MemberBoardResponse approvalMemberBoards = memberBoardService.noApprovalMemberBoard(memberBoardId);
        return success(approvalMemberBoards);
    }
}

