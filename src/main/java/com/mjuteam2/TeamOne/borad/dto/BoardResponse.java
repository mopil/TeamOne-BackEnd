package com.mjuteam2.TeamOne.borad.dto;

import com.mjuteam2.TeamOne.borad.domain.BoardStatus;
import com.mjuteam2.TeamOne.borad.domain.BoardType;
import com.mjuteam2.TeamOne.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor @Builder
public class BoardResponse {
    private Long boardId;
    private String title;
    private String content;
    private int viewCount;
    private BoardType boardType;
    private int memberCount;
    private String classTitle;
    private String classDate;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
    private BoardStatus boardStatus;

    private List<Comment> comments;

}
