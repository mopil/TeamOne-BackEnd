package com.mjuteam2.TeamOne.message.controller;

import com.mjuteam2.TeamOne.message.dto.MessageRequestForm;
import com.mjuteam2.TeamOne.message.dto.MessageResponse;
import com.mjuteam2.TeamOne.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 메시지 삭제
     */
}
