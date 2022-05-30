package com.mjuteam2.TeamOne.borad.service;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.domain.BoardStatus;
import com.mjuteam2.TeamOne.borad.domain.BoardType;
import com.mjuteam2.TeamOne.borad.dto.request.AppealBoardForm;
import com.mjuteam2.TeamOne.borad.dto.request.BoardForm;
import com.mjuteam2.TeamOne.borad.dto.request.FreeBoardForm;
import com.mjuteam2.TeamOne.borad.dto.request.WantedBoardForm;
import com.mjuteam2.TeamOne.borad.dto.response.BoardListResponse;
import com.mjuteam2.TeamOne.borad.dto.response.BoardResponse;
import com.mjuteam2.TeamOne.borad.exception.BoardException;
import com.mjuteam2.TeamOne.borad.repository.BoardRepository;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.exception.MemberException;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
    private final MemberRepository memberRepository;

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
        Board saved = boardRepository.save(board);
        saved.addCurrentMemberCount();

        Member findFromRepoMember = memberRepository.findById(loginMember.getId()).orElseThrow(() -> new MemberException("해당 유저 조회 실패."));
        findFromRepoMember.addBoard(saved);
        return board;
    }


    /**
     * 게시글 하나 조회
     */
    @Transactional
    public Board findByBoardId(Long boardId) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new BoardException("게시글 조회 오류."));
        findBoard.addViewCount();
        return findBoard;
    }

    /**
     * 게시글 전체 조회
     */
    public BoardListResponse findAll() {
        List<BoardResponse> result = new ArrayList<>();
        boardRepository.findAll().forEach(board -> result.add(board.toResponse()));
        return new BoardListResponse(result);
    }

    // 페이징 처리 해서 전체 조회
    public BoardListResponse findAll(Pageable pageable) {
        List<BoardResponse> content = boardRepository.findAll(pageable).map(Board::toResponse).getContent();
        return new BoardListResponse(content);
    }
    
    // 문자열 게시글 타입을 BoardType 객체로 변환
    private BoardType getBoardType(String boardType) {
        switch (boardType) {
            case WANTED:
                return BoardType.WANTED;
            case APPEAL:
                return BoardType.APPEAL;
            default:
                return BoardType.FREE;
        }
    }
    
    // 게시글 타입으로 전체 조회
    public BoardListResponse findAllByType(String boardType) {
        BoardType type = getBoardType(boardType);
        List<BoardResponse> result = new ArrayList<>();
        // 순회를 돌면서 Board -> BoardResponse 변환
        boardRepository.findAllByType(type).forEach(board -> result.add(board.toResponse()));
        log.info("결과 = {}", result);
        return new BoardListResponse(result);
    }

    // 내가 쓴 글 타입별로 전체 조회
    public BoardListResponse findWrittenAllByType(Member loginMember, String boardType) {
        BoardType type = getBoardType(boardType);
        List<BoardResponse> result = new ArrayList<>();
        boardRepository.findWrittenAllByType(loginMember.getId(), type).forEach(board -> result.add(board.toResponse()));
        return new BoardListResponse(result);
    }

    /**
     * 게시글 검색 조회 (검색 기능)
     */
    public BoardListResponse search(String searchWay, String keyword) {
        List<BoardResponse> result = new ArrayList<>();
        switch (searchWay) {
            case "TITLE" :
                boardRepository.searchByTitle(keyword)
                    .forEach(board -> result.add(board.toResponse()));
            case "CONTENT":
                boardRepository.searchByContent(keyword)
                        .forEach(board -> result.add(board.toResponse()));
            case "TITLE_CONTENT":
                boardRepository.searchByTitleAndContent(keyword)
                        .forEach(board -> result.add(board.toResponse()));
            case "CLASS":
                boardRepository.searchByClassTitle(keyword)
                        .forEach(board -> result.add(board.toResponse()));

        }
        return new BoardListResponse(result);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Board update(Member loginMember, Long boardId, BoardForm form) {
        Member findFromMember = memberRepository.findById(loginMember.getId()).orElseThrow(() -> new MemberException("해당 유저 조회 실패"));
        Long findFromMemberId = findFromMember.findBoard(boardId);
        Board findFromRepo = findByBoardId(boardId);
        if (!findFromMemberId.equals(findFromRepo.getMember().getId())) throw new BoardException("글쓴이와 로그인 한 사용자가 다릅니다.");

        if (form instanceof WantedBoardForm) {
            WantedBoardForm wantedForm = (WantedBoardForm) form;
            findFromRepo.updateWanted(wantedForm.getTitle(), wantedForm.getMemberCount(), wantedForm.getClassTitle(), wantedForm.getClassDate(), wantedForm.getContent());
        } else if (form instanceof AppealBoardForm) {
            AppealBoardForm appealForm = (AppealBoardForm) form;
            findFromRepo.updateAppeal(appealForm.getTitle(), appealForm.getClassTitle(), appealForm.getClassDate(), appealForm.getContent());
        } else {
            FreeBoardForm freeForm = (FreeBoardForm) form;
            findFromRepo.updateFree(freeForm.getTitle(), freeForm.getContent());
        }
        return findFromRepo;
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public boolean deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
        return true;
    }

    /**
     * 모집 완료
     */
    @Transactional
    public Board finishBoard(Long boardId) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new BoardException("게시글이 존재하지 않습니다."));
        findBoard.changeBoardStatus(BoardStatus.OK);
        return findBoard;
    }
}
