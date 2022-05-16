package com.mjuteam2.TeamOne.borad.dto.response;

import com.mjuteam2.TeamOne.comment.dto.CommentResponse;
import com.mjuteam2.TeamOne.member.dto.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class FreeBoardResponse extends BoardResponse{
    // 게시판 공용이자 자유게시판 양식
    private Long boardId;
    private String title;
    private String content;
    private int viewCount;
    private String createdDate;
    private String updatedDate;
    private List<CommentResponse> comments;
    private MemberResponse writer;
}
