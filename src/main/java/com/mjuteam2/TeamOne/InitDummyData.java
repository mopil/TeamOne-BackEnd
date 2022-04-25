package com.mjuteam2.TeamOne;

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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Transactional
public class InitDummyData {

    private final SignUpService signUpService;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void userDummyData() {
        SignUpForm form = SignUpForm.builder()
                .nickname("슈퍼테스터")
                .department("컴퓨터공학과")
                .email("test1234@mju.ac.kr")
                .userId("test1234")
                .userName("테스터1")
                .password("123456")
                .passwordCheck("123456")
                .phoneNumber("010-1234-1234")
                .authToken("ASDFASDF")
                .schoolId("60171442")
                .build();

        signUpService.signUp(form);

    }

    @PostConstruct
    public void boardDummyData() {
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

    }
}
