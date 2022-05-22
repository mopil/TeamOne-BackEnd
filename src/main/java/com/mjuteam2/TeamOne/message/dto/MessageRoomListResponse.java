package com.mjuteam2.TeamOne.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class MessageRoomListResponse {

    private List<MessageRoomResponse> messageRoomList;
}
