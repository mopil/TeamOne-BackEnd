package com.mjuteam2.TeamOne.borad.repository;

import com.mjuteam2.TeamOne.borad.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
