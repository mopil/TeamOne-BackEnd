package com.mjuteam2.TeamOne.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class CommentResponse {
    private Long commentId;
    private Long BoardId;
    private Long memberId;
    private String content;
    private LocalDateTime createdAt;

}
