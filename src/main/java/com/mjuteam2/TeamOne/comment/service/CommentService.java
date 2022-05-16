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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    private Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException("게시글을 찾을 수 없습니다."));
    }

    /**
     * 댓글 작성
     */
    public Comment createComment(Member loginMember, Long boardId, CommentForm form) {
        Board writeBoard = getBoard(boardId);

        Comment comment = form.toEntity(loginMember, writeBoard);
        commentRepository.save(comment);
        return comment;
    }

    /**
     * 댓글 조회
     */
    // 댓글 한 개 조회
    @Transactional(readOnly = true)
    public Comment findByCommentId(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException("댓글이 없습니다"));
    }

    // 해당 게시글에 있는 모든 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponse> findAllByBoardId(Long boardId) {
        getBoard(boardId);
        List<CommentResponse> result = new ArrayList<>();
        commentRepository.findAllByBoardId(boardId).forEach(comment -> result.add(comment.toResponse()));
        return result;
    }

    // 모든 댓글 조회 (관리자용)
    public List<CommentResponse> findAll() {
        List<CommentResponse> result = new ArrayList<>();
        commentRepository.findAll().forEach(comment -> result.add(comment.toResponse()));
        return result;
    }

    /**
     * 댓글 수정
     */
    public Comment updateComment(Member loginMember, Long commentId, CommentForm form) {
        Comment writeComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException("댓글을 찾을 수 없습니다."));

        Long userId1 = loginMember.getId();
        Long userId2 = writeComment.getMember().getId();
        if (!Objects.equals(userId1, userId2)) {
            throw new CommentException("글쓴이와 로그인 한 사용자가 다릅니다.");
        }
        writeComment.update(form.getContent());
        return writeComment;
    }
    
    /**
     * 댓글 삭제
     */
    public boolean deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
        return true;
    }
}
