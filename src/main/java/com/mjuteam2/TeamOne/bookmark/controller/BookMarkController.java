package com.mjuteam2.TeamOne.bookmark.controller;

import com.mjuteam2.TeamOne.bookmark.dto.BookMarkResponse;
import com.mjuteam2.TeamOne.bookmark.service.BookMarkService;
import com.mjuteam2.TeamOne.borad.exception.BoardException;
import com.mjuteam2.TeamOne.member.config.Login;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.util.dto.BoolResponse;
import com.mjuteam2.TeamOne.util.dto.ErrorResponse;
import com.mjuteam2.TeamOne.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mjuteam2.TeamOne.util.dto.RestResponse.badRequest;
import static com.mjuteam2.TeamOne.util.dto.RestResponse.success;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookMarkController {

    private final BookMarkService bookMarkService;

    /**
     * 북마크 생성
     */
    @PostMapping("/new/{boardId}")
    public ResponseEntity<?> createBookMark(@Login Member loginMember,
                                            @PathVariable Long boardId) {

        BookMarkResponse bookMark = bookMarkService.createBookMark(loginMember, boardId);
        return success(bookMark);
    }

    /**
     * 북마크 삭제
     */
    @DeleteMapping("/{bookmarkId}")
    public ResponseEntity<?> deleteBookMark(@PathVariable Long bookmarkId) {
        return success(new BoolResponse(bookMarkService.deleteBookMark(bookmarkId)));
    }

    /**
     * 북마크 한 개 조회
     */
    @GetMapping("/{bookMarkId}")
    public ResponseEntity<?> findByBookMarkId(@PathVariable Long bookMarkId) {
        return success(bookMarkService.findByBookMarkId(bookMarkId));
    }

    /**
     * 북마크 전체 조회
     */
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return success(bookMarkService.findAll());
    }

    /**
     * 해당 유저 북마크 전체 조회
     */
    @GetMapping("/all/{memberId}")
    public ResponseEntity<?> findAllByMemberId(@PathVariable Long memberId) {
        return success(bookMarkService.findByMemberId(memberId));
    }

    /**
     *  예외 처리
     */
    @ExceptionHandler(BoardException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> bookMarkExHandle(BoardException e) {
        log.error("북마크 예외 발생 : 핸들러 작동");
        return badRequest(new ErrorResponse(ErrorCode.BOARD_ERROR, e.getMessage()));
    }
}
