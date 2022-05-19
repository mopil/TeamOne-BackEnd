package com.mjuteam2.TeamOne.caution.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CautionListResponse {

    private List<CautionResponse> cautions;

}
