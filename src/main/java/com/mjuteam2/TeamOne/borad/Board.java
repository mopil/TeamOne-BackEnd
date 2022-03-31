package com.mjuteam2.TeamOne.borad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjuteam2.TeamOne.bookmark.BookMark;
import com.mjuteam2.TeamOne.comment.Comment;
import com.mjuteam2.TeamOne.member.Member;
import com.mjuteam2.TeamOne.member.MemberBoard;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;
    private int viewCount;

    private int memberCount;
    private String classTitle;
    private String classDate;
    private Date deadline;

    @CreationTimestamp
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private BoardStatus boardStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<MemberBoard> memberBoardList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    @JsonIgnore
    private List<BookMark> bookMarkList = new ArrayList<>();

}
