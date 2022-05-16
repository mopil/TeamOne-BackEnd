package com.mjuteam2.TeamOne.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class MessageResponse {
    private Long messageId;
    private String senderUserId;
    private String receiverUserId;
    private String content;
    private String createdDate;
}
