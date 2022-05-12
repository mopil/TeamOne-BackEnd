package com.mjuteam2.TeamOne;

import com.mjuteam2.TeamOne.bookmark.service.BookMarkService;
import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.domain.BoardStatus;
import com.mjuteam2.TeamOne.borad.domain.BoardType;
import com.mjuteam2.TeamOne.borad.repository.BoardRepository;
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
        ArrayList<Board> boardList = new ArrayList<>();
        for (int i = 0 ; i<5; i++) {
            Board board = Board.builder()
                    .title("게시물 테스트"+i)
                    .content("테스트입니다")
                    .boardType(BoardType.WANTED)
                    .writer(tester)
                    .memberCount(i)
                    .boardStatus(BoardStatus.DEFAULT)
                    .classTitle("자료구조")
                    .classDate("월요일")
                    .build();
            boardList.add(board);
        }
        for (int i = 5 ; i<10; i++) {
            Board board = Board.builder()
                    .title("게시물 테스트"+i)
                    .content("이것또한테스트")
                    .boardType(BoardType.FREE)
                    .writer(tester)
                    .memberCount(i)
                    .boardStatus(BoardStatus.DEFAULT)
                    .classTitle("팀프")
                    .classDate("화요일")
                    .build();
            boardList.add(board);
        }
        for (int i = 10 ; i<13; i++) {
            Board board = Board.builder()
                    .title("게시물 테스트"+i)
                    .content("배고프다")
                    .boardType(BoardType.APPEAL)
                    .writer(tester)
                    .memberCount(i+100)
                    .boardStatus(BoardStatus.DEFAULT)
                    .classTitle("상커의 요리교실")
                    .classDate("일요일")
                    .build();
            boardList.add(board);
        }
        boardRepository.saveAll(boardList);


        // 북마크 더미데이터 세팅
        boardRepository.findByWriterName("배성흥").forEach(board -> {
            bookMarkService.createBookMark(tester, board.getId());
        });

    }
}
