package com.mjuteam2.TeamOne.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjuteam2.TeamOne.bookmark.BookMark;
import com.mjuteam2.TeamOne.borad.Board;
import com.mjuteam2.TeamOne.caution.CautionList;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<BookMark> bookMarkList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<CautionList> cautionLists = new ArrayList<>();

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
