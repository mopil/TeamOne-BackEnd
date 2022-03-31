package com.mjuteam2.TeamOne.member;

import lombok.*;

import javax.persistence.*;

@Entity @Data @Builder
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NonNull private String userId;
    @NonNull private String password;
    @NonNull private String email;
    @NonNull private String userName;
    @NonNull private String nickname;
    @NonNull private Integer score;
    @NonNull private String introduce;

    @Enumerated(EnumType.STRING)
    @NonNull
    private MemberType memberType;

    public Member() {

    }


    //profile 사진 해야함

}
