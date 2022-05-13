package com.mjuteam2.TeamOne.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookMarkListResponse {

    private List<BookMarkResponse> bookMarks;

}
