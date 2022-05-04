package com.mjuteam2.TeamOne.caution.domain;

import com.mjuteam2.TeamOne.caution.dto.CautionResponse;
import com.mjuteam2.TeamOne.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;


@Entity
@Getter
public class Caution {

    @Id @GeneratedValue
    @Column(name = "caution_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member requestMember; // 차단을 요청한 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    private Member targetMember; // 차단된 사용자

    @Builder
    public Caution(Member requestMember, Member cautionedMember) {
        this.requestMember = requestMember;
        this.targetMember = cautionedMember;
    }

    protected Caution() {
    }

    public CautionResponse toResponse() {
        return CautionResponse.builder()
                .cautionId(id)
                .requestMember(requestMember.toResponse())
                .cautionedMember(targetMember.toResponse())
                .build();
    }
}
