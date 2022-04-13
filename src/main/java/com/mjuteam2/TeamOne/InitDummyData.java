package com.mjuteam2.TeamOne;

import com.mjuteam2.TeamOne.borad.domain.Board;
import com.mjuteam2.TeamOne.borad.domain.BoardStatus;
import com.mjuteam2.TeamOne.borad.domain.BoardType;
import com.mjuteam2.TeamOne.borad.dto.BoardForm;
import com.mjuteam2.TeamOne.borad.repository.BoardRepository;
import com.mjuteam2.TeamOne.borad.service.BoardService;
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

    @PostConstruct
    public void userDummyData() {
        SignUpForm form = SignUpForm.builder()
                .nickname("슈퍼테스터")
                .department("컴퓨터공학과")
                .email("test1234@mju.ac.kr")
                .id("test1234")
                .userName("테스터1")
                .password("tester1234")
                .passwordCheck("tester1234")
                .phoneNumber("010-1234-1234")
                .authToken("ASDFASDF")
                .schoolId("60171442")
                .build();

        signUpService.signUp(form);

    }

    @PostConstruct
    public void boardDummyData() {
        Member tester = memberRepository.findByUserId("test1234");
        ArrayList<Board> boardList = new ArrayList<>();
        for (int i = 0 ; i<10; i++) {
            Board board = Board.builder()
                    .title("게시물 테스트"+i)
                    .content("테스트입니다")
                    .boardType(BoardType.WANTED)
                    .writer(tester)
                    .boardStatus(BoardStatus.DEFAULT)
                    .classTitle("자료구조")
                    .classDate("월요일")
                    .build();
            boardList.add(board);
        }
        boardRepository.saveAll(boardList);

    }
}
