package com.mjuteam2.TeamOne.borad.service;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.domain.BoardType;
import com.mjuteam2.TeamOne.borad.dto.*;
import com.mjuteam2.TeamOne.borad.exception.BoardException;
import com.mjuteam2.TeamOne.borad.repository.BoardRepository;
import com.mjuteam2.TeamOne.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    public static final String WANTED = "wanted";
    public static final String APPEAL = "appeal";
    public static final String FREE = "free";

    private final BoardRepository boardRepository;

    /**
     * 게시글 생성
     */
    @Transactional
    public Board save(Member loginMember, BoardForm form) {
        Board board;
        if (form instanceof WantedBoardForm) {
            board = form.toBoard(loginMember, BoardType.WANTED);
        } else if (form instanceof AppealBoardForm) {
            board = form.toBoard(loginMember, BoardType.APPEAL);
        } else {
            board = form.toBoard(loginMember, BoardType.FREE);
        }
        boardRepository.save(board);
        loginMember.addBoard(board);
        return board;
    }


    /**
     * 게시글 하나 조회
     */
    public Board findByBoardId(Long boardId) {
       return boardRepository.findById(boardId).orElseThrow(() -> new BoardException("게시글 조회 오류."));
    }

    /**
     * 게시글 전체 조회
     */
    public List<BoardResponse> findAll() {
        List<BoardResponse> result = new ArrayList<>();
        boardRepository.findAll().forEach(board -> result.add(board.toResponse()));
        return result;
    }

    // 게시글 타입으로 전체 조회
    public BoardListResponse findAllByType(String boardType) {
        BoardType type;
        switch (boardType) {
            case WANTED:
                type = BoardType.WANTED;
                break;
            case APPEAL:
                type = BoardType.APPEAL;
                break;
            default:
                type = BoardType.FREE;
                break;
        }
        List<BoardResponse> result = new ArrayList<>();
        // 순회를 돌면서 Board -> BoardResponse 변환
        boardRepository.findAllByType(type).forEach(board -> result.add(board.toResponse()));
        return new BoardListResponse(result);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public BoardResponse update(Member loginMember, Long boardId, WantedBoardForm form) {
        Long findFromMemberId = loginMember.findBoard(boardId);
        Board findFromRepo = findByBoardId(boardId);
        if (!findFromMemberId.equals(findFromRepo.getMember().getId())) throw new BoardException("글쓴이와 로그인 한 사용자가 다릅니다.");
        findFromRepo.updateWanted(form.getTitle(), form.getMemberCount(), form.getClassTitle(), form.getClassDate(), form.getContent());
        return findFromRepo.toResponse();
    }

    /**
     * 게시글 삭제
     */
    public boolean deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
        return true;
    }
}
