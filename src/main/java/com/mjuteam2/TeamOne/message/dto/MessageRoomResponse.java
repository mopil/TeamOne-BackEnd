package com.mjuteam2.TeamOne.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageRoomResponse {
    private Long messageRoomId;
    private Long senderId;
    private Long receiverId;
    private String senderUserId;
    private String receiverUserId;
    private String createdDate;
    private String receiverNickname;
}