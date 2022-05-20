package com.mjuteam2.TeamOne.comment.controller;

import com.mjuteam2.TeamOne.borad.exception.BoardException;
import com.mjuteam2.TeamOne.comment.domain.Comment;
import com.mjuteam2.TeamOne.comment.dto.CommentForm;
import com.mjuteam2.TeamOne.comment.dto.CommentListResponse;
import com.mjuteam2.TeamOne.comment.service.CommentService;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.service.MemberService;
import com.mjuteam2.TeamOne.util.dto.BoolResponse;
import com.mjuteam2.TeamOne.util.dto.ErrorResponse;
import com.mjuteam2.TeamOne.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static com.mjuteam2.TeamOne.util.dto.ErrorResponse.convertJson;
import static com.mjuteam2.TeamOne.util.dto.RestResponse.badRequest;
import static com.mjuteam2.TeamOne.util.dto.RestResponse.success;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;

    private void logError(List<FieldError> errors) {
        log.error("comment Errors = {}", errors);
    }

    /**
     * 댓글 생성
     */
    @PostMapping("/boards/{boardId}/comments")
    public ResponseEntity<?> createComment(HttpServletRequest request,
                                           @PathVariable Long boardId,
                                           @Valid @RequestBody CommentForm form,
                                           BindingResult bindingResult) throws LoginException {
        if (bindingResult.hasErrors()) {
            logError(bindingResult.getFieldErrors());
            return badRequest(convertJson(bindingResult.getFieldErrors()));
        }
        Member loginMember = memberService.getLoginMember(request);
        Comment comment = commentService.createComment(loginMember, boardId, form);
        return success(comment.toResponse());
    }

    /**
     * 댓글 조회
     */
    // 댓글 하나 조회
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<?> findByCommentId(@PathVariable Long commentId) {
        return success(commentService.findByCommentId(commentId));
    }

    // 게시글에 있는 댓글 모두 조회
    @GetMapping("/boards/{boardId}/comments")
    public ResponseEntity<?> findByCommentAll(@PathVariable Long boardId) {
        CommentListResponse allByBoardId = commentService.findAllByBoardId(boardId);
        return success(allByBoardId);
    }

    // 모든 댓글 조회 (관리자용)
    @GetMapping("/comments/all")
    public ResponseEntity<?> findAll() {
        CommentListResponse all = commentService.findAll();
        return success(all);
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<?> updateComment(HttpServletRequest request,
                                           @PathVariable Long commentId,
                                           @Valid @RequestBody CommentForm form,
                                           BindingResult bindingResult) throws LoginException {
        if (bindingResult.hasErrors()) {
            logError(bindingResult.getFieldErrors());
            return badRequest(convertJson(bindingResult.getFieldErrors()));
        }
        Member loginMember = memberService.getLoginMember(request);
        Comment comment = commentService.updateComment(loginMember, commentId, form);
        return success(comment.toResponse());
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        return success(new BoolResponse(commentService.deleteComment(commentId)));
    }

    /**
     *  예외 처리
     */
    @ExceptionHandler(BoardException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> commentExHandle(BoardException e) {
        log.error("댓글 예외 발생 : 핸들러 작동");
        return badRequest(new ErrorResponse(ErrorCode.BOARD_ERROR, e.getMessage()));
    }
}
