package com.mjuteam2.TeamOne.borad.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjuteam2.TeamOne.bookmark.BookMark;
import com.mjuteam2.TeamOne.comment.Comment;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.domain.MemberBoard;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;
    private int viewCount;


    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    private int memberCount;
    private String classTitle;
    private String classDate;
    private LocalDateTime deadline;

    @CreationTimestamp
    private LocalDateTime createdAt;

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

    @Builder
    public Board(Member writer, String title, String content, BoardType boardType, int memberCount, String classTitle, String classDate, LocalDateTime deadline, BoardStatus boardStatus) {
        this.member = writer;
        this.title = title;
        this.content = content;
        this.boardType = boardType;
        this.memberCount = memberCount;
        this.classTitle = classTitle;
        this.classDate = classDate;
        this.deadline = deadline;
        this.boardStatus = boardStatus;
    }
}
