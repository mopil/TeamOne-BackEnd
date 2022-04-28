package com.mjuteam2.TeamOne.badge.repository;

import com.mjuteam2.TeamOne.badge.domain.Badge;
import com.mjuteam2.TeamOne.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

    List<Badge> findAllByMember(Member member);
}
