package com.mjuteam2.TeamOne.comment.dto;

import com.mjuteam2.TeamOne.member.dto.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class CommentResponse {
    private Long commentId;
    private Long boardId;
    private MemberResponse writer;
    private String content;
    private LocalDateTime createdAt;
}
