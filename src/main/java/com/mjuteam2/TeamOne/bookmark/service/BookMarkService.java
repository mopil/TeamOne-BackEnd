package com.mjuteam2.TeamOne.bookmark.service;

import com.mjuteam2.TeamOne.bookmark.domain.BookMark;
import com.mjuteam2.TeamOne.bookmark.dto.BookMarkListResponse;
import com.mjuteam2.TeamOne.bookmark.dto.BookMarkResponse;
import com.mjuteam2.TeamOne.bookmark.exception.BookMarkException;
import com.mjuteam2.TeamOne.bookmark.repository.BookMarkRepository;
import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.exception.BoardException;
import com.mjuteam2.TeamOne.borad.repository.BoardRepository;
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
public class BookMarkService {

    private final BookMarkRepository bookMarkRepository;
    private final BoardRepository boardRepository;

    /**
     * 북마크 생성
     */
    @Transactional
    public BookMarkResponse createBookMark(Member loginMember, Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException("게시글을 찾을 수 없습니다."));

        List<BookMark> byBoardId = bookMarkRepository.findAllByBoardId(boardId);

        if(!byBoardId.isEmpty()){
            throw new BookMarkException("이미 북마크 된 게시물 입니다.");
        }

        BookMark saveBookMark = BookMark.builder()
                .createdAt(LocalDateTime.now())
                .member(loginMember)
                .board(board)
                .build();

        bookMarkRepository.save(saveBookMark);
        return saveBookMark.toResponse();
    }


    /**
     * 북마크 삭제
     */
    // 북마크 아이디로 하나 삭제
    public boolean deleteBookMark(Long bookMarkId) {
        bookMarkRepository.deleteById(bookMarkId);
        return true;
    }

    // 로그인한 사용자의 북마크 전체 삭제
    public boolean deleteAllBookMarks(Member loginMember) {
        List<BookMark> bookMarks = bookMarkRepository.findAllByMemberId(loginMember.getId());
        bookMarkRepository.deleteAll(bookMarks);
        return true;
    }


    /**
     * 북마크 한 개 조회
     */
    public BookMarkResponse findByBookMarkId(Long bookMarkId) {
        BookMark bookMark = bookMarkRepository.findById(bookMarkId)
                .orElseThrow(() -> new BookMarkException("북마크가 존재하지 않습니다."));
        return bookMark.toResponse();
    }

    /**
     * 로그인된 유저의 북마크 전체 조회
     */
    public BookMarkListResponse findByMemberId(Member loginMember) {
        List<BookMarkResponse> result = new ArrayList<>();
        bookMarkRepository.findAllByMemberId(loginMember.getId()).forEach(bookMark -> result.add(bookMark.toResponse()));
        return new BookMarkListResponse(result);
    }

    /**
     * 북마크 전체 조회 (모든 북마크)
     */
    public List<BookMarkResponse> findAll() {
        List<BookMarkResponse> result = new ArrayList<>();
        bookMarkRepository.findAll().forEach(bookMark -> result.add(bookMark.toResponse()));
        return result;
    }
}
