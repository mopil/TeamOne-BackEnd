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

    // 종료된 MemberBoard 조회 (memberId) 기준
    @Query("SELECT m FROM MemberBoard m WHERE m.member.id = :memberId and m.admission = :approval and m.board.boardStatus = 'OK'")
    List<MemberBoard> findAllByMemberBoardByMemberId(@Param("memberId") Long memberId, @Param("approval") Admission approval);

    // 종료된 MemberBoard 조회 (boardId) 기준
    @Query("SELECT m FROM MemberBoard m WHERE m.board.id = :boardId and m.admission = :approval and m.board.boardStatus = 'OK' and m.member.id <> :memberId")
    List<MemberBoard> findAllByMemberBoardByBoardId(@Param("memberId") Long memberId, @Param("boardId") Long boardId, @Param("approval") Admission approval);
}
