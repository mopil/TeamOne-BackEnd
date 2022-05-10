package com.mjuteam2.TeamOne.message.controller;

import com.mjuteam2.TeamOne.message.dto.MessageRequestForm;
import com.mjuteam2.TeamOne.message.dto.MessageResponse;
import com.mjuteam2.TeamOne.message.exception.MessageException;
import com.mjuteam2.TeamOne.message.service.MessageService;
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
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    /**
     * 메시지 생성 (작성)
     * 메시지 수정은 없음
     */
    @PostMapping("")
    public ResponseEntity<?> createMessage(@RequestBody MessageRequestForm form) {
        MessageResponse savedMessage = messageService.createMessage(form);
        return success(savedMessage);
    }

    /**
     * 메시지 조회
     */
    // 메시지 ID로 하나 조회
    @GetMapping("/{messageId}")
    public ResponseEntity<?> getMessage(@PathVariable Long messageId) {
        return success(messageService.findById(messageId));
    }
    // 보낸 사람 기준으로 보낸 메시지 전체 조회
    @GetMapping("/sent-message/{senderId}")
    public ResponseEntity<?> getMessageBySender(@PathVariable Long senderId) {
        return success(messageService.findAllSent(senderId));
    }
    // 받은 사람 기준으로 받은 메시지 전체 조회
    @GetMapping("/received-message/{receiverId}")
    public ResponseEntity<?> getMessageByReceiver(@PathVariable Long receiverId) {
        return success(messageService.findAllReceived(receiverId));
    }

    /**
     * 메시지 삭제
     */
    // 메시지 ID로 하나 삭제
    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteById(@PathVariable Long messageId) {
        messageService.delete(messageId);
        return success(new BoolResponse(true));
    }

    // 받은 메시지 전체 삭제
    @DeleteMapping("/received-message/{receiverId}")
    public ResponseEntity<?> deleteAll(@PathVariable Long receiverId) {
        messageService.deleteAll(receiverId);
        return success(new BoolResponse(true));
    }

    /**
     *  예외 처리
     */
    @ExceptionHandler(MessageException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> messageExHandle(MessageException e) {
        log.error("쪽지 예외 발생 : 핸들러 작동");
        return badRequest(new ErrorResponse(ErrorCode.MESSAGE_ERROR, e.getMessage()));
    }
}
