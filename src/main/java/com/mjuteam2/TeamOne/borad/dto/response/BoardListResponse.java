package com.mjuteam2.TeamOne.borad.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class BoardListResponse {
    private List<BoardResponse> boards;
}
