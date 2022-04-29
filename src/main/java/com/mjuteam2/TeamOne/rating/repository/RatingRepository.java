package com.mjuteam2.TeamOne.rating.repository;

import com.mjuteam2.TeamOne.rating.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
