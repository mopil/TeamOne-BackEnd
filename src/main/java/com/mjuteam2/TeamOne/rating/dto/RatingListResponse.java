package com.mjuteam2.TeamOne.rating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RatingListResponse {
    private List<RatingResponse> ratings;
}
