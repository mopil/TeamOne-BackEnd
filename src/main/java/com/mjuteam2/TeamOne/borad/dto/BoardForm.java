package com.mjuteam2.TeamOne.borad.dto;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.domain.BoardType;
import com.mjuteam2.TeamOne.member.domain.Member;

public interface BoardForm {
    Board toBoard(Member writer, BoardType boardType);
}
