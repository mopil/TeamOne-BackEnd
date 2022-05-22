package com.mjuteam2.TeamOne.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageRoomRequestForm {
    private Long senderId;
    private Long receiverId;
}
