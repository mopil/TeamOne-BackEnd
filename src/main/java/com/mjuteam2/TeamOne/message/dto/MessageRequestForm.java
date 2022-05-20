package com.mjuteam2.TeamOne.message.dto;

import lombok.Data;

@Data
public class MessageRequestForm {
    private Long senderId;
    private Long receiverId;
    private String content;
    private Long messageRoomId;
}
