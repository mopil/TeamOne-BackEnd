package com.mjuteam2.TeamOne.comment.repository;

import com.mjuteam2.TeamOne.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Modifying(clearAutomatically = true)
    @Query("SELECT c FROM Comment c where c.board.id = :id")
    List<Comment> selectComment(@Param("id") Long id);
}
