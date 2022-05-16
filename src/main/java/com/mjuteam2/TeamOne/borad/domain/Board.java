package com.mjuteam2.TeamOne.borad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mjuteam2.TeamOne.bookmark.domain.BookMark;
import com.mjuteam2.TeamOne.borad.dto.response.AppealBoardResponse;
import com.mjuteam2.TeamOne.borad.dto.response.BoardResponse;
import com.mjuteam2.TeamOne.borad.dto.response.FreeBoardResponse;
import com.mjuteam2.TeamOne.borad.dto.response.WantedBoardResponse;
import com.mjuteam2.TeamOne.comment.domain.Comment;
import com.mjuteam2.TeamOne.comment.dto.CommentResponse;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.domain.MemberBoard;
import com.mjuteam2.TeamOne.util.domain.BaseTimeEntity;
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
public class Board extends BaseTimeEntity {

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
    private String deadline;

    @Enumerated(EnumType.STRING)
    private BoardStatus boardStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<MemberBoard> memberBoardList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BookMark> bookMarkList = new ArrayList<>();

    @Builder
    public Board(Member writer, String title, String content, BoardType boardType, int memberCount, String classTitle, String classDate, BoardStatus boardStatus) {
        this.member = writer;
        this.title = title;
        this.content = content;
        this.boardType = boardType;
        this.memberCount = memberCount;
        this.classTitle = classTitle;
        this.classDate = classDate;
        this.boardStatus = boardStatus;
    }

    public BoardResponse toResponse(BoardType type) {
        if (type == BoardType.WANTED) return toWantedResponse();
        else if (type == BoardType.APPEAL) return toAppealResponse();
        else return toFreeResponse();
    }

    public AppealBoardResponse toAppealResponse() {
        List<CommentResponse> result = new ArrayList<>();
        comments.forEach(c -> result.add(c.toResponse()));
        return AppealBoardResponse.builder()
                .boardId(id)
                .title(title)
                .content(content)
                .viewCount(viewCount)
                .createdDate(getCreatedDate())
                .updatedDate(getUpdatedDate())
                .comments(result)
                .writer(member.toResponse())
                .classTitle(classTitle)
                .classDate(classDate)
                .build();
    }

    public WantedBoardResponse toWantedResponse() {
        List<CommentResponse> result = new ArrayList<>();
        comments.forEach(c -> result.add(c.toResponse()));
        return WantedBoardResponse.builder()
                .boardId(id)
                .title(title)
                .content(content)
                .viewCount(viewCount)
                .createdDate(getCreatedDate())
                .updatedDate(getUpdatedDate())
                .comments(result)
                .writer(member.toResponse())
                .boardStatus(boardStatus)
                .deadline(deadline)
                .classTitle(classTitle)
                .classDate(classDate)
                .build();
    }

    public FreeBoardResponse toFreeResponse() {
        List<CommentResponse> result = new ArrayList<>();
        comments.forEach(c -> result.add(c.toResponse()));
        return FreeBoardResponse.builder()
                .boardId(id)
                .title(title)
                .content(content)
                .viewCount(viewCount)
                .createdDate(getCreatedDate())
                .updatedDate(getUpdatedDate())
                .comments(result)
                .writer(member.toResponse())
                .build();
    }

    /**
     * 게시글 수정 메소드
     */
    // 어필해요
    public void updateAppeal(String newTitle, String newClassTitle, String newClassDate, String newContent) {
        this.title = newTitle;
        this.classTitle = newClassTitle;
        this.classDate = newClassDate;
        this.content = newContent;
    }

    // 팀원구해요
    public void updateWanted(String newTitle, int newMemberCount, String newClassTitle, String newClassDate, String newContent) {
        this.title = newTitle;
        this.memberCount = newMemberCount;
        this.classTitle = newClassTitle;
        this.classDate = newClassDate;
        this.content = newContent;
    }

    // 자유
    public void updateFree(String newTitle, String newContent) {
        this.title = newTitle;
        this.content = newContent;
    }

    // 조회수 증가
    public void addViewCount() {
        this.viewCount += 1;
    }
}
