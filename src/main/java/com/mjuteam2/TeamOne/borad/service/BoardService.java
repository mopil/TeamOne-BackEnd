package com.mjuteam2.TeamOne.borad.service;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.domain.BoardType;
import com.mjuteam2.TeamOne.borad.dto.BoardForm;
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

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시글 생성
     */
    @Transactional
    public Board save(String userId, BoardForm form, BoardType boardType) {
        Member writer = memberRepository.findByUserId(userId).orElse(null);
        Board board = form.toBoard(writer, boardType);
        boardRepository.save(board);
        return board;
    }


    /**
     * 게시글 하나 조회
     */
    public Board findByBoardId(Long boardId) {
        return boardRepository.findById(boardId).orElse(null);
    }

    /**
     * 게시글 전체 조회
     */
    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    /**
     * 게시글 수정
     */

    /**
     * 게시글 삭제
     */
}
