package com.mjuteam2.TeamOne.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjuteam2.TeamOne.badge.domain.Badge;
import com.mjuteam2.TeamOne.bookmark.BookMark;
import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.caution.CautionList;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity @Getter @ToString
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userId; 
    private String password;
    private String email;

    private String userName; // 이름
    private String department; // 학과
    private String schoolId; // 학번
    private String phoneNumber;
    private String nickname;
    private String introduce; // 간략 자기소개

    @Column(name = "token")
    private String authToken;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @OneToOne
    @JoinColumn(name = "member_value_id")
    private MemberValue memberValue;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BookMark> bookMarkList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CautionList> cautionLists = new ArrayList<>();



    @Builder
    public Member(String userId, String password, String email, String userName, String department, String schoolId, String phoneNumber, String nickname, String authToken, MemberType memberType) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.userName = userName;
        this.department = department;
        this.schoolId = schoolId;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.authToken = authToken;
        this.memberType = memberType;
    }

    protected Member() {

    }

    //profile 사진 해야함
    @Column(name = "profile")
    private String profileUrl;

    /**
     * 게시글 비즈니스 로직
     */
    // 새로운 게시글을 작성하면 추가
    public void addBoard(Board board) {
        boards.add(board);
    }

    // 내가 쓴 글 boardId로 찾기
    public Long findBoard(Long boardId) {
        for (Board b : boards) {
            if (Objects.equals(b.getId(), boardId)) {
                return b.getMember().getId();
            }
        }
        return null;
    }


    /**
     * 뱃지 비즈니스 로직
     */
    // 뱃지 추가
    public void addBadge(Badge badge) {memberValue.getBadges().add(badge);}

    // 레이팅 : 평점과 뱃지 추가
    public void addRating(double star, Badge badge) {
        memberValue.addRating(star, badge);
    }

    /**
     *  회원 수정
     */
    public void addPoint(int amount) {
        this.memberValue.addPoint(amount);
    }
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void updatePassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

}
