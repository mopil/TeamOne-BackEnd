package com.mjuteam2.TeamOne.bookmark.dto;

import com.mjuteam2.TeamOne.borad.dto.response.BoardResponse;
import com.mjuteam2.TeamOne.member.dto.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BookMarkResponse {
    private Long bookMarkId;
    private MemberResponse writer;
    private BoardResponse board;
    private String createdDate;

}
