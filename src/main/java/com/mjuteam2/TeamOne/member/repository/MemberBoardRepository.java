package com.mjuteam2.TeamOne.member.repository;

import com.mjuteam2.TeamOne.member.domain.Admission;
import com.mjuteam2.TeamOne.member.domain.MemberBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberBoardRepository extends JpaRepository<MemberBoard, Long> {

    @Query("SELECT m FROM MemberBoard m WHERE m.board.id = :boardId")
    List<MemberBoard> findAllByBoardId(@Param("boardId") Long boardId);

    @Query("SELECT m FROM MemberBoard m WHERE m.member.id = :memberId")
    List<MemberBoard> findAllByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT m FROM MemberBoard m WHERE m.board.id = :boardId and m.admission = :approval")
    List<MemberBoard> findAllByAdmission_Approval(@Param("boardId") Long boardId, @Param("approval") Admission approval);

    @Query("SELECT m FROM MemberBoard m WHERE m.board.id = :boardId and m.member.id = :memberId")
    Optional<MemberBoard> findByMemberIdAndBoardId(@Param("memberId") Long memberId, @Param("boardId") Long boardId);
}
