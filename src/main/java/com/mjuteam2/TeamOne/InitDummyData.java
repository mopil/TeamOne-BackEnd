package com.mjuteam2.TeamOne;

import com.mjuteam2.TeamOne.bookmark.service.BookMarkService;
import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.domain.BoardStatus;
import com.mjuteam2.TeamOne.borad.domain.BoardType;
import com.mjuteam2.TeamOne.borad.repository.BoardRepository;
import com.mjuteam2.TeamOne.caution.service.CautionService;
import com.mjuteam2.TeamOne.comment.dto.CommentForm;
import com.mjuteam2.TeamOne.comment.service.CommentService;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.dto.SignUpForm;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import com.mjuteam2.TeamOne.member.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class InitDummyData {

    private final SignUpService signUpService;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BookMarkService bookMarkService;
    private final CommentService commentService;
    private final CautionService cautionService;

    @PostConstruct
    public void dummyData() {
        // 사용자 더미데이터 세팅
        SignUpForm form = SignUpForm.builder()
                .nickname("슈퍼테스터")
                .department("컴퓨터공학과")
                .email("mopil1102@naver.com")
                .userId("test1234")
                .userName("배성흥")
                .password("123456")
                .passwordCheck("123456")
                .phoneNumber("010-1234-1234")
                .authToken("ASDFASDF")
                .schoolId("60171442")
                .build();
        SignUpForm form2 = SignUpForm.builder()
                .nickname("밤토리")
                .department("컴퓨터공학과")
                .email("mopil1102@mju.ac.kr")
                .userId("mopil1102")
                .userName("이범준")
                .password("123456")
                .passwordCheck("123456")
                .phoneNumber("010-1234-1234")
                .authToken("ASDFASDF")
                .schoolId("60162284")
                .build();
        signUpService.signUp(form);
        signUpService.signUp(form2);

        // 게시판 더미데이터 세팅
        Member tester = memberRepository.findByUserId("test1234").orElse(null);
        Member tester2 = memberRepository.findByUserId("mopil1102").orElse(null);
        ArrayList<Board> boardList = new ArrayList<>();
        for (int i = 0 ; i<5; i++) {
            Board board = Board.builder()
                    .title("어필해요!!" + i)
                    .content("제 자신을 어필해요")
                    .boardType(BoardType.APPEAL)
                    .writer(tester)
                    .memberCount(i)
                    .currentMemberCount(1)
                    .boardStatus(BoardStatus.DEFAULT)
                    .classTitle("자료구조")
                    .classDate("월요일")
                    .build();
            boardList.add(board);
        }
        for (int i = 5 ; i<10; i++) {
            Board board = Board.builder()
                    .title("팀원 구합니다~"+i)
                    .content("참여 잘하는 팀원 구합니다~~")
                    .boardType(BoardType.WANTED)
                    .writer(tester)
                    .memberCount(i)
                    .currentMemberCount(1)
                    .boardStatus(BoardStatus.DEFAULT)
                    .classTitle("팀프2")
                    .classDate("화요일")
                    .build();
            boardList.add(board);
        }
        for (int i = 10 ; i<13; i++) {
            Board board = Board.builder()
                    .title("자유게시물 입니다"+i)
                    .content("제 뱃지를 자랑해요")
                    .boardType(BoardType.FREE)
                    .writer(tester)
                    .memberCount(i+100)
                    .currentMemberCount(1)
                    .boardStatus(BoardStatus.DEFAULT)
                    .build();
            boardList.add(board);
        }
        boardRepository.saveAll(boardList);


        // 북마크 더미데이터 세팅
        boardRepository.findByWriterName("배성흥").forEach(board -> {
            bookMarkService.createBookMark(tester, board.getId());
        });

        // 댓글 더미데이터 세팅
        Board board = boardRepository.findById(3L).get();
        commentService.createComment(tester, board.getId(), new CommentForm("발표 수고하셨어요!"));
        commentService.createComment(tester, board.getId(), new CommentForm("팀장님 사랑합니다~"));

        // 유의 더미데이터 세팅
        cautionService.setCaution(tester.getId(), tester2.getId());

    }
}
