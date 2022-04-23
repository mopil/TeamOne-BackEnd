package com.mjuteam2.TeamOne.borad.controller;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.dto.AppealBoardForm;
import com.mjuteam2.TeamOne.borad.dto.FreeBoardForm;
import com.mjuteam2.TeamOne.borad.dto.WantedBoardForm;
import com.mjuteam2.TeamOne.borad.exception.BoardException;
import com.mjuteam2.TeamOne.borad.service.BoardService;
import com.mjuteam2.TeamOne.member.config.Login;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.util.dto.ErrorResponse;
import com.mjuteam2.TeamOne.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.mjuteam2.TeamOne.util.dto.ErrorResponse.convertJson;
import static com.mjuteam2.TeamOne.util.dto.RestResponse.badRequest;
import static com.mjuteam2.TeamOne.util.dto.RestResponse.success;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    private void logError(List<FieldError> errors) {
        log.error("Board Errors = {}", errors);
    }

    /**
     * 게시글 생성 왜 PathVariable로 받지 않고 나누었는가? form을 다르게 받아야 하기 때문에
     */
    // 팀원구해요
    @PostMapping("/new/wanted")
    public ResponseEntity<?> createWantedBoard(@Login Member loginMember,
                                         @Valid @RequestBody WantedBoardForm form,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logError(bindingResult.getFieldErrors());
            return badRequest(convertJson(bindingResult.getFieldErrors()));
        }
        Board savedBoard = boardService.save(loginMember, form);
        return success(savedBoard);
    }

    // 어필해요
    @PostMapping("/new/appeal")
    public ResponseEntity<?> createAppealBoard(@Login Member loginMember,
                                         @Valid @RequestBody AppealBoardForm form,
                                         BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            logError(bindingResult.getFieldErrors());
            return badRequest(convertJson(bindingResult.getFieldErrors()));
        }
        Board savedBoard = boardService.save(loginMember, form);
        return success(savedBoard);
    }

    // 자유게시판
    @PostMapping("/new/free")
    public ResponseEntity<?> createFreeBoard(@Login Member loginMember,
                                         @Valid @RequestBody FreeBoardForm form,
                                         BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            logError(bindingResult.getFieldErrors());
            return badRequest(convertJson(bindingResult.getFieldErrors()));
        }
        Board savedBoard = boardService.save(loginMember, form);
        return success(savedBoard);
    }


    /**
     * 게시글 조회
     */
    // 게시글 id로 하나만 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<?> findByBoardId(@PathVariable Long boardId) {
        return success(boardService.findByBoardId(boardId));
    }

    // 타입 상관없이 전체 게시글 조회
    @GetMapping("/all")
    public ResponseEntity<?> findAllBoards() {
        return success(boardService.findAllBoards());
    }
    
    // 게시글 타입별로 전체 조회
    @GetMapping("/all/{boardType}")
    public ResponseEntity<?> findAllByType(@PathVariable String boardType) {
        return success(boardService.findAllWanted(boardType));
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
    public ResponseEntity<?> boardExHandle(BoardException e) {
        log.error("[exceptionHandle] ex", e);
        return badRequest(new ErrorResponse(ErrorCode.BOARD_ERROR, e.getMessage()));
    }

}
