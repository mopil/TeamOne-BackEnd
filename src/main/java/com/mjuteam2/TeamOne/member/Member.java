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

    private String userName; // 이름
    private String department; // 학과
    private int schoolId; // 학번
    private String phoneNumber;

    private String nickname;
    private double stars; // 기여도 평점 (감소되는것)
    private int points; // RPG 점수 (계속 쌓는것)
    private String introduce; // 간략 자기소개

    @Column(name = "token")
    private String signUpToken;

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
    public Member(String userId, String password, String email, String userName, String department, int schoolId, String phoneNumber, String nickname, String signUpToken) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.userName = userName;
        this.department = department;
        this.schoolId = schoolId;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.signUpToken = signUpToken;
    }

    protected Member() {

    }


    //profile 사진 해야함

}
