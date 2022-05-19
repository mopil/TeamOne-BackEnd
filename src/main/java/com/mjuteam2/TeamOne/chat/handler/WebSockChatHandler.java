//package com.mjuteam2.TeamOne.chat.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mjuteam2.TeamOne.chat.ChatService;
//
//import com.mjuteam2.TeamOne.chat.domain.ChatMessage;
//import com.mjuteam2.TeamOne.chat.domain.ChatRoom;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class WebSockChatHandler extends TextWebSocketHandler {
//
//    private final ObjectMapper objectMapper;
//    private final ChatService chatService;
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("payload {}", payload);
////        TextMessage textMessage = new TextMessage("Welcome chatting server");
////        session.sendMessage(textMessage);
//
//        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
//        ChatRoom roomById = chatService.findRoomById(chatMessage.getRoomId());
//        roomById.handleActions(session, chatMessage, chatService);
//    }
//}
