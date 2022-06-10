package com.mjuteam2.TeamOne.rating.repository;

import com.mjuteam2.TeamOne.member.domain.MemberBoard;
import com.mjuteam2.TeamOne.message.domain.Message;
import com.mjuteam2.TeamOne.rating.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT m FROM Rating m WHERE m.memberBoard.id = :memberBoardId and m.ratedMember.id = :ratedMemberId and m.ratingMember.id = :ratingMemberId")
    Optional<Rating> findRating(@Param("memberBoardId") Long memberBoardId, @Param("ratingMemberId") Long ratingMemberId, @Param("ratedMemberId") Long ratedMemberId);

    @Query("SELECT m FROM Rating m WHERE m.ratingMember.id = :ratingMemberId")
    List<Rating> findAllByRatingMemberId(@Param("ratingMemberId") Long ratingMemberId);


    @Query("SELECT m FROM Rating m WHERE m.ratedMember.id = :ratedMemberId")
    List<Rating> findAllRatingByRatedMember3(@Param("ratedMemberId") Long ratedMemberId);


}
