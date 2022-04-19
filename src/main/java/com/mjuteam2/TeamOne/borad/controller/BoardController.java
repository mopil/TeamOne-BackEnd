package com.mjuteam2.TeamOne.borad.controller;

import com.mjuteam2.TeamOne.borad.domain.BoardType;
import com.mjuteam2.TeamOne.borad.dto.BoardForm;
import com.mjuteam2.TeamOne.borad.exception.BoardException;
import com.mjuteam2.TeamOne.borad.service.BoardService;
import com.mjuteam2.TeamOne.util.dto.RestResponse;
import com.mjuteam2.TeamOne.util.exception.ErrorCode;
import com.mjuteam2.TeamOne.util.exception.ErrorDto;
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
    @PostMapping("/{id}/new")
    public ResponseEntity<?> createBoard(@PathVariable Long id,
                                         @RequestParam BoardType boardType,
                                         @Valid @RequestBody BoardForm form,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Board Errors = {}", bindingResult.getFieldErrors());
            return RestResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }
        return RestResponse.success(boardService.save(id, form, boardType));
    }


    /**
     * 게시글 하나 조회
     */
    @GetMapping("/{boardId}")
    public ResponseEntity<?> findByBoardId(@PathVariable Long boardId) {
        return RestResponse.success(boardService.findByBoardId(boardId));
    }

    /**
     * 게시글 전체 조회
     */
    @GetMapping("")
    public ResponseEntity<?> findAllBoards() {
        return RestResponse.success(boardService.findAllBoards());
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/{id}/")

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
        return RestResponse.badRequest(new ErrorDto(ErrorCode.BOARD_ERROR, e.getMessage()));
    }

}
