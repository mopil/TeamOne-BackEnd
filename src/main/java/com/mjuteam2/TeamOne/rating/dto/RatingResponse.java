package com.mjuteam2.TeamOne.rating.dto;

import com.mjuteam2.TeamOne.badge.domain.Badge;
import com.mjuteam2.TeamOne.member.dto.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class RatingResponse {

    private Long ratingId;
    private Long memberBoardId;
    private MemberResponse ratingMember;
    private MemberResponse ratedMember;
    private double star;

}
