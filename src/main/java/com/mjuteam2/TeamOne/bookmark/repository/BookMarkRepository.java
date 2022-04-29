package com.mjuteam2.TeamOne.bookmark.repository;

import com.mjuteam2.TeamOne.bookmark.domain.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    @Modifying(clearAutomatically = true)
    @Query("SELECT b FROM BookMark b where b.member.id = :id")
    List<BookMark> findByMemberId(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("SELECT b FROM BookMark b where b.board.id = :id")
    List<BookMark> findByBoardId(@Param("id") Long id);
}
