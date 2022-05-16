package com.mjuteam2.TeamOne.borad.dto.response;

import com.mjuteam2.TeamOne.comment.dto.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@Builder
public class AppealBoardResponse extends BoardResponse {
    
    // 게시판 공용
    private Long boardId;
    private String title;
    private String content;
    private int viewCount;
    private LocalDateTime createdAt;
    private List<CommentResponse> comments;
    
    // 어필해요 전용
    private String classTitle;
    private String classDate;
}
