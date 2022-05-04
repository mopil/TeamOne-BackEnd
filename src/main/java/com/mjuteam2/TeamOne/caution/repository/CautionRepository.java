package com.mjuteam2.TeamOne.caution.repository;

import com.mjuteam2.TeamOne.caution.domain.Caution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CautionRepository extends JpaRepository<Caution, Long> {

    @Query("SELECT c FROM Caution c WHERE c.targetMember.id = :cautionedMemberId")
    Optional<Caution> findByCautionedMemberId(@Param("cautionedMemberId") Long cautionedMemberId);

    @Query("SELECT c FROM Caution c WHERE c.requestMember.id = :requestMemberId")
    List<Caution> findAllByRequestMemberId(@Param("requestMemberId") Long requestMemberId);

    @Query("SELECT c FROM Caution c WHERE c.requestMember.id = :requestMemberId AND c.targetMember.id = :targetMemberId")
    Optional<Caution> duplicateCheck(@Param("requestMemberId") Long requestMemberId, @Param("targetMemberId") Long targetMemberId);



}
