package com.mjuteam2.TeamOne.message.controller;

import com.mjuteam2.TeamOne.message.domain.MessageRoom;
import com.mjuteam2.TeamOne.message.dto.MessageRoomRequestForm;
import com.mjuteam2.TeamOne.message.dto.MessageRoomResponse;
import com.mjuteam2.TeamOne.message.exception.MessageException;
import com.mjuteam2.TeamOne.message.service.MessageRoomService;
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
@RequestMapping("/room")
public class MessageRoomController {

    private final MessageRoomService messageRoomService;

    /**
     * 채팅방 생성
     */
    @PostMapping("")
    public ResponseEntity<?> createMessageRoom(@RequestBody MessageRoomRequestForm form) {
        MessageRoomResponse messageRoom = messageRoomService.createMessageRoom(form);
        return success(messageRoom);
    }

    /**
     * 채팅방 조회
     */

    // 채팅방 하나 조회
    @GetMapping("/{messageRoomId}")
    public ResponseEntity<?> getMessageRoom(@PathVariable Long messageRoomId) {
        return success(messageRoomService.findMessageRoom(messageRoomId));
    }

    // 채팅방 전체 조회(받은 사람 기준)
    @GetMapping("/all/{receiverId}")
    public ResponseEntity<?> getMessageRoomByReceiver(@PathVariable Long receiverId) {
        return success(messageRoomService.findMessageRoomByReceiver(receiverId));
    }

    /**
     * 채팅방 삭제
     */
    @DeleteMapping("/{messageRoomId}")
    public ResponseEntity<?> delete(@PathVariable Long messageRoomId) {
        messageRoomService.delete(messageRoomId);
        return success(new BoolResponse(true));
    }

    /**
     *  예외 처리
     */
    @ExceptionHandler(MessageException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> messageExHandle(MessageException e) {
        log.error("채팅방 예외 발생 : 핸들러 작동");
        return badRequest(new ErrorResponse(ErrorCode.MESSAGE_ERROR, e.getMessage()));
    }
}
