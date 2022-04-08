package com.mjuteam2.TeamOne.member.repository;

import com.mjuteam2.TeamOne.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByNickname(String nickname);
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
}
