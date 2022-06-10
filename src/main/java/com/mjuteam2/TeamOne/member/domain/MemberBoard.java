package com.mjuteam2.TeamOne.member.domain;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.member.dto.MemberBoardResponse;
import com.mjuteam2.TeamOne.member.dto.MemberResponse;
import com.mjuteam2.TeamOne.rating.domain.Rating;
import com.mjuteam2.TeamOne.util.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberBoard extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_board_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Admission admission;

    @OneToMany(mappedBy = "memberBoard")
    private List<Rating> ratings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;



    @Builder
    public MemberBoard(Member member, Board board, Admission admission) {
        this.member = member;
        this.board = board;
        this.admission = admission;
    }


    public MemberBoardResponse toResponse() {
        return MemberBoardResponse.builder()
                .memberBoardId(id)
                .Admission(admission.toString())
                .memberId(member.getId())
                .nickname(member.getNickname())
                .createdDate(getCreatedDate())
                .build();
    }
    // 생성 메서드
    public void changeAdmission(Admission admission) {
        this.admission = admission;
    }
}
