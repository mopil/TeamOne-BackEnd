package com.mjuteam2.TeamOne.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class BookMarkResponse {

    private Long bookMarkId;
    private Long boardId;
    private Long memberId;
    private LocalDateTime createdAt;

}
