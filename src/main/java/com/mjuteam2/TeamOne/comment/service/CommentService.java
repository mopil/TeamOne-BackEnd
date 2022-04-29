package com.mjuteam2.TeamOne.comment.service;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.exception.BoardException;
import com.mjuteam2.TeamOne.borad.repository.BoardRepository;
import com.mjuteam2.TeamOne.comment.domain.Comment;
import com.mjuteam2.TeamOne.comment.dto.CommentForm;
import com.mjuteam2.TeamOne.comment.dto.CommentResponse;
import com.mjuteam2.TeamOne.comment.exception.CommentException;
import com.mjuteam2.TeamOne.comment.repository.CommentRepository;
import com.mjuteam2.TeamOne.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    /**
     * 댓글 작성
     */
    @Transactional
    public CommentResponse createComment(Member loginMember, Long boardId, CommentForm form) {
        Board writeBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException("게시글을 찾을 수 없습니다."));

        Comment comment = form.toComment(loginMember, writeBoard);

        commentRepository.save(comment);

        return new CommentResponse(comment.getId(), loginMember.getId(), writeBoard.getId(), comment.getContent(), LocalDateTime.now());
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public CommentResponse updateComment(Member loginMember, Long commentId, CommentForm form) {
        Comment writeComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException("댓글을 찾을 수 없습니다."));

        Long userId1 = loginMember.getId();
        Long userId2 = writeComment.getMember().getId();
        if (userId1 != userId2) {
            throw new CommentException("글쓴이와 로그인 한 사용자가 다릅니다.");
        }
        writeComment.update(form.getComment());
        return writeComment.toResponse();

    }


    /**
     * 댓글 삭제
     */
    public boolean deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
        return true;
    }

    /**
     * 댓글 한 개 보기
     */
    public Comment findByCommentId(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException("댓글이 없습니다"));
    }

    /**
     * 해당 게시글에 모든 댓글 조회
     */
    public List<CommentResponse> findAllByBoardId(Long boardId) {
        boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException("게시글을 찾을 수 없습니다."));
        List<CommentResponse> result = new ArrayList<>();
        commentRepository.selectComment(boardId).forEach(comment -> result.add(comment.toResponse()));
        return result;
    }

    /**
     * 모든 댓글 조회
     */
    public List<CommentResponse> findAll() {
        List<CommentResponse> result = new ArrayList<>();
        commentRepository.findAll().forEach(comment -> result.add(comment.toResponse()));
        return result;
    }


    // 댓글 수정

    // 댓글 삭제

}
