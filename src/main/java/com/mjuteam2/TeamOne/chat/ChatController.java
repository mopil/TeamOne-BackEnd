package com.mjuteam2.TeamOne.chat;

import com.mjuteam2.TeamOne.chat.domain.ChatMessage;
import com.mjuteam2.TeamOne.chat.domain.ChatRoom;
import com.mjuteam2.TeamOne.chat.domain.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (MessageType.ENTER.equals(message.getType()))
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        messagingTemplate.convertAndSend("/topic/chat/room/" + message.getRoomId(), message);
    }

}
