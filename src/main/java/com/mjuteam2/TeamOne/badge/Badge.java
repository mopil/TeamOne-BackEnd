package com.mjuteam2.TeamOne.badge;

import com.mjuteam2.TeamOne.rating.Rating;
import lombok.Getter;

import javax.persistence.*;

@Entity @Getter
public class Badge {

    @Id @GeneratedValue
    @Column(name = "badge_id")
    private Long id;

    private String keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_id")
    private Rating rating;


}
