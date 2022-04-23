package com.mjuteam2.TeamOne.borad.service;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.domain.BoardType;
import com.mjuteam2.TeamOne.borad.dto.AppealBoardForm;
import com.mjuteam2.TeamOne.borad.dto.BoardForm;
import com.mjuteam2.TeamOne.borad.dto.FreeBoardForm;
import com.mjuteam2.TeamOne.borad.dto.WantedBoardForm;
import com.mjuteam2.TeamOne.borad.exception.BoardException;
import com.mjuteam2.TeamOne.borad.repository.BoardRepository;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boards")
@Transactional(readOnly = true)
public class BoardService {

    public static final String WANTED = "wanted";
    public static final String APPEAL = "appeal";
    public static final String FREE = "free";

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시글 생성
     */
    @Transactional
    public Board save(Member loginMember, BoardForm form) {
        Board board;
        if (form instanceof WantedBoardForm) {
            board = ((WantedBoardForm) form).toBoard(loginMember, BoardType.WANTED);
        } else if (form instanceof AppealBoardForm) {
            board = ((AppealBoardForm) form).toBoard(loginMember, BoardType.APPEAL);
        } else {
            board = ((FreeBoardForm) form).toBoard(loginMember, BoardType.FREE);
        }
        boardRepository.save(board);
        return board;
    }


    /**
     * 게시글 하나 조회
     */
    public Board findByBoardId(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException("게시글 조회 오류."));
    }

    /**
     * 게시글 전체 조회
     */
    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    // 게시글 타입으로 전체 조회
    public List<Board> findAllWanted(String boardType) {
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
        return boardRepository.findAllByType(type);
    }

    /**
     * 게시글 수정
     */
//    public Board update(Long boardId, BoardForm form) {
//        Board foundBoard = findByBoardId(boardId);
//    }

    /**
     * 게시글 삭제
     */
}
