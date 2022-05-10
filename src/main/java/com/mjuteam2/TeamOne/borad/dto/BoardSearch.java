package com.mjuteam2.TeamOne.borad.dto;

import lombok.Data;

@Data
public class BoardSearch {
    private BoardSearchType boardSearchType;
    private String keyword;
}
