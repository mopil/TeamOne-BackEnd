package com.mjuteam2.TeamOne.member.repository;

import com.mjuteam2.TeamOne.member.domain.MemberBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBoardRepository extends JpaRepository<MemberBoard, Long> {
}
