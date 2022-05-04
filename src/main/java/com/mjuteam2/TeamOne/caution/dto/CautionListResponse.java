package com.mjuteam2.TeamOne.caution.dto;

import com.mjuteam2.TeamOne.member.dto.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CautionListResponse {

    private List<MemberResponse> cautionList;

}
