package com.mjuteam2.TeamOne.borad.dto.response;

import com.mjuteam2.TeamOne.borad.domain.BoardStatus;
import com.mjuteam2.TeamOne.comment.dto.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class WantedBoardResponse extends BoardResponse{
    // 게시판 공용
    private Long boardId;
    private String title;
    private String content;
    private int viewCount;
    private String createdDate;
    private String updatedDate;
    private List<CommentResponse> comments;

    // 팀원구해요 전용
    private int memberCount;
    private String classTitle;
    private String classDate;
    private String deadline;
    private BoardStatus boardStatus;
}
