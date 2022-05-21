package com.mjuteam2.TeamOne.bookmark.controller;

import com.mjuteam2.TeamOne.bookmark.dto.BookMarkListResponse;
import com.mjuteam2.TeamOne.bookmark.dto.BookMarkResponse;
import com.mjuteam2.TeamOne.bookmark.service.BookMarkService;
import com.mjuteam2.TeamOne.borad.exception.BoardException;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.service.MemberService;
import com.mjuteam2.TeamOne.util.dto.BoolResponse;
import com.mjuteam2.TeamOne.util.dto.ErrorResponse;
import com.mjuteam2.TeamOne.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import static com.mjuteam2.TeamOne.util.dto.RestResponse.badRequest;
import static com.mjuteam2.TeamOne.util.dto.RestResponse.success;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
public class BookMarkController {

    private final BookMarkService bookMarkService;
    private final MemberService memberService;

    /**
     * 북마크 생성
     */
    @PostMapping("/new/{boardId}")
    public ResponseEntity<?> createBookMark(HttpServletRequest request,
                                            @PathVariable Long boardId) throws LoginException {
        Member loginMember = memberService.getLoginMember(request);
        BookMarkResponse bookMark = bookMarkService.createBookMark(loginMember, boardId);
        return success(bookMark);
    }

    /**
     * 북마크 삭제
     */
    // 북마크 아이디로 하나 삭제
    @DeleteMapping("/{bookmarkId}")
    public ResponseEntity<?> deleteBookMark(@PathVariable Long bookmarkId) {
        return success(new BoolResponse(bookMarkService.deleteBookMark(bookmarkId)));
    }

    // 로그인한 사용자의 북마크 전체 삭제
    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllBookMarks(HttpServletRequest request) throws LoginException {
        Member loginMember = memberService.getLoginMember(request);
        return success(new BoolResponse(bookMarkService.deleteAllBookMarks(loginMember)));
    }

    /**
     * 북마크 한 개 조회
     */
    // 북마크 id로 하나 조회
    @GetMapping("/{bookMarkId}")
    public ResponseEntity<?> findByBookMarkId(@PathVariable Long bookMarkId) {
        return success(bookMarkService.findByBookMarkId(bookMarkId));
    }

//    /**
//     * 북마크 전체 조회
//     */
//    @GetMapping("/all")
//    public ResponseEntity<?> findAll(HttpServletRequest request) {
//        return success(bookMarkService.findAll());
//    }

    /**
     * 해당 유저 북마크 전체 조회
     */
    @GetMapping("/all")
    public ResponseEntity<?> findAllByMemberId(HttpServletRequest request) throws LoginException {
        Member loginMember = memberService.getLoginMember(request);
        BookMarkListResponse bookMarkList = bookMarkService.findByMemberId(loginMember);
        return success(bookMarkList);
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
