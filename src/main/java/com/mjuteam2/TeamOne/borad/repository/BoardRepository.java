package com.mjuteam2.TeamOne.borad.repository;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.domain.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // 타입으로 게시글 전체 조회
    @Query("SELECT b FROM Board b WHERE b.boardType = :boardType")
    List<Board> findAllByType(@Param("boardType") BoardType boardType);

    // 사용자가 쓴 글 게시글 타입별로 조회
    @Query("SELECT b FROM Board b WHERE b.boardType = :boardType AND b.member.id = :memberId")
    List<Board> findWrittenAllByType(@Param("memberId") Long memberId, @Param("boardType") BoardType boardType);

    // 제목으로 검색
    @Query("SELECT b FROM Board b WHERE b.title LIKE %:title%")
    List<Board> searchByTitle(@Param("title") String title);

    // 내용으로 검색
    @Query("SELECT b FROM Board b WHERE b.content LIKE %:content%")
    List<Board> searchByContent(@Param("content") String content);

    // 내용+제목으로 검색
    @Query("SELECT b FROM Board b WHERE b.title LIKE %:keyword% OR b.content LIKE %:keyword%")
    List<Board> searchByTitleAndContent(@Param("keyword") String keyword);

    // 수업이름으로 검색
    @Query("SELECT b FROM Board b WHERE b.classTitle LIKE %:classTitle%")
    List<Board> searchByClassTitle(@Param("classTitle") String classTitle);

    // 게시글 작성자 이름으로 검색
    @Query("SELECT b FROM Board b WHERE b.member.userName = :userName")
    List<Board> findByWriterName(String userName);
}
