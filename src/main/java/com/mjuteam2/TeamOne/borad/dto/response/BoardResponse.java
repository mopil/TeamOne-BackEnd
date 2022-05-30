package com.mjuteam2.TeamOne.borad.dto.response;

import com.mjuteam2.TeamOne.borad.domain.BoardStatus;
import com.mjuteam2.TeamOne.borad.domain.BoardType;
import com.mjuteam2.TeamOne.comment.dto.CommentResponse;
import com.mjuteam2.TeamOne.member.dto.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class BoardResponse {
    // 게시판 공용
    private Long boardId;
    private String title;
    private String content;
    private int viewCount;
    private String createdDate;
    private String updatedDate;
    private List<CommentResponse> comments;
    private MemberResponse writer;
    private BoardStatus boardStatus;
    private BoardType boardType;
    
    // +어필해요 추가 속성
    private String classTitle;
    private String classDate;

    // +팀원구해요 추가 속성
    private int memberCount;
    private int currentMemberCount;
    private String deadline;
}
