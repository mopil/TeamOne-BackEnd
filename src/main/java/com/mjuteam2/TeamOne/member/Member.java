package com.mjuteam2.TeamOne.member;

import lombok.*;

import javax.persistence.*;

@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String password;
    private String email;
    private String userName;
    private String nickname;
    private Integer score;
    private String introduce;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Builder
    public Member(String userId, String password, String email, String userName, String nickname, MemberType memberType) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.userName = userName;
        this.nickname = nickname;
        this.memberType = memberType;
    }

    public Member() {

    }


    //profile 사진 해야함

}
