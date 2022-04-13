package com.mjuteam2.TeamOne.borad.controller;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.domain.BoardType;
import com.mjuteam2.TeamOne.borad.dto.BoardForm;
import com.mjuteam2.TeamOne.borad.exception.BoardException;
import com.mjuteam2.TeamOne.borad.service.BoardService;
import com.mjuteam2.TeamOne.common.dto.ApiResponse;
import com.mjuteam2.TeamOne.common.error.ErrorCode;
import com.mjuteam2.TeamOne.common.error.ErrorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시글 생성 /boards/new?boardType=free
     */
    @PostMapping("/{userId}/new")
    public ResponseEntity<?> createBoard(@PathVariable String userId,
                                         @RequestParam BoardType boardType,
                                         @Valid @RequestBody BoardForm form,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Board Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }
        Board savedBoard = boardService.save(userId, form, boardType);
        if (savedBoard == null) throw new BoardException("게시글 저장 오류");

        return ApiResponse.success(savedBoard);
    }


    /**
     * 게시글 하나 조회
     */
    @GetMapping("/{boardId}")
    public ResponseEntity<?> findByBoardId(@PathVariable Long boardId) {
        Board foundedBoard = boardService.findByBoardId(boardId);
        if (foundedBoard == null) throw new BoardException("존재하지 않는 게시글 ID 입니다");
        return ApiResponse.success(foundedBoard);
    }

    /**
     * 게시글 전체 조회
     */
    @GetMapping("")
    public ResponseEntity<?> findAllBoards() {
        return ApiResponse.success(boardService.findAllBoards());
    }

    /**
     * 게시글 수정
     */

    /**
     * 게시글 삭제
     */

    /**
     *  예외 처리
     */
    // 게시글 예외
    @ExceptionHandler(BoardException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> signUpExHandle(BoardException e) {
        log.error("[exceptionHandle] ex", e);
        return ApiResponse.badRequest(new ErrorDto(ErrorCode.BOARD_ERROR, e.getMessage()));
    }

}
