package com.mjuteam2.TeamOne.rating.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjuteam2.TeamOne.badge.domain.Badge;
import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.domain.MemberBoard;
import com.mjuteam2.TeamOne.rating.dto.RatingResponse;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class Rating {

    @Id @GeneratedValue
    @Column(name = "rating_id")
    private Long id;

    // 소수점도 평가 가능 ex) 4.5
    private double star;

    // 뱃지
    @OneToMany(mappedBy = "rating", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Badge> badges = new ArrayList<>();

    // 평가 하는 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_member_id")
    private Member ratingMember;

    // 평가 받는 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rated_member_id")
    private Member ratedMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_board_id")
    private MemberBoard memberBoard;

    @Builder
    //public Rating(Member ratedMember, Member ratingMember, MemberBoard memberBoard, double star,  List<Badge> badge) {
    public Rating(Member ratedMember, Member ratingMember, MemberBoard memberBoard, double star) {
        this.ratingMember = ratingMember;
        this.ratedMember = ratedMember;
        this.memberBoard = memberBoard;
        this.star = star;
        //this.badges = badge;
    }

    protected Rating() {

    }

    public RatingResponse toResponse() {
        return RatingResponse.builder()
                .ratingId(id)
                .ratedMember(ratedMember.toResponse())
                .ratingMember(ratingMember.toResponse())
                .memberBoardId(memberBoard.getId())
                .star(star)
                //.badgeList(badges)
                .build();
    }
}
