package com.mjuteam2.TeamOne.comment.controller;

import com.mjuteam2.TeamOne.borad.exception.BoardException;
import com.mjuteam2.TeamOne.comment.dto.CommentForm;
import com.mjuteam2.TeamOne.comment.dto.CommentResponse;
import com.mjuteam2.TeamOne.comment.service.CommentService;
import com.mjuteam2.TeamOne.member.config.Login;
import com.mjuteam2.TeamOne.member.domain.Member;
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

import javax.validation.Valid;
import java.util.List;

import static com.mjuteam2.TeamOne.util.dto.ErrorResponse.convertJson;
import static com.mjuteam2.TeamOne.util.dto.RestResponse.badRequest;
import static com.mjuteam2.TeamOne.util.dto.RestResponse.success;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boards/comments")
public class CommentController {

    private final CommentService commentService;

    private void logError(List<FieldError> errors) {
        log.error("comment Errors = {}", errors);
    }

    /**
     * 댓글 생성
     */
    @PostMapping("/new/{boardId}")
    public ResponseEntity<?> createComment(@Login Member loginMember,
                                           @PathVariable Long boardId,
                                           @Valid @RequestBody CommentForm form,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logError(bindingResult.getFieldErrors());
            return badRequest(convertJson(bindingResult.getFieldErrors()));
        }
        CommentResponse comment = commentService.createComment(loginMember, boardId, form);
        return success(comment);
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@Login Member loginMember,
                                           @PathVariable Long commentId,
                                           @Valid @RequestBody CommentForm form,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logError(bindingResult.getFieldErrors());
            return badRequest(convertJson(bindingResult.getFieldErrors()));
        }
        CommentResponse commentResponse = commentService.updateComment(loginMember, commentId, form);
        return success(commentResponse);
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        return success(new BoolResponse(commentService.deleteComment(commentId)));
    }

    /**
     * 댓글 조회 (한 개)
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<?> findByCommentId(@PathVariable Long commentId) {
        return success(commentService.findByCommentId(commentId));
    }

    /**
     * 댓글 조회 (게시글에 있는거 전부)
     */
    @GetMapping("/all/{boardId}")
    public ResponseEntity<?> findByCommentAll(@PathVariable Long boardId) {
        return success(commentService.findAllByBoardId(boardId));
    }

    /**
     * 댓글 조회 (모든 댓글 조회)
     */
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
       return success(commentService.findAll());
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
