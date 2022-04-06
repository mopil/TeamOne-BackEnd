package com.mjuteam2.TeamOne;

import com.mjuteam2.TeamOne.member.Member;
import com.mjuteam2.TeamOne.member.MemberRepository;
import com.mjuteam2.TeamOne.member.SignUpDto;
import com.mjuteam2.TeamOne.member.SignUpService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class SignUpServiceTest {

    @Autowired
    SignUpService signUpService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(value = false)
    void success() throws Exception {
        // given
        SignUpDto form = SignUpDto.builder()
                .nickname("미오")
                .department("컴퓨터공학과")
                .email("mopil1102@naver.com")
                .id("mopil1102")
                .userName("테스터1")
                .password("tester1234")
                .passwordCheck("tester1234")
                .phoneNumber("010-1234-1234")
                .signUpToken("ASDFASDF")
                .schoolId(60171442)
                .build();

        // when
        Member newMember = signUpService.signUp(form);

        // then
        Assertions.assertThat(newMember).isEqualTo(memberRepository.findById(newMember.getId()).get());
    }

    @Test
    @Rollback
    void fail_duplicatedEmail() throws Exception {
        // given
        SignUpDto form1 = SignUpDto.builder()
                .nickname("테스터1")
                .department("컴퓨터공학과")
                .email("mopil1102@naver.com")
                .id("mopil1102")
                .userName("테스터1")
                .password("tester1234")
                .passwordCheck("tester1234")
                .phoneNumber("010-1234-1234")
                .signUpToken("ASDFASDF")
                .schoolId(60171442)
                .build();
        SignUpDto form2 = SignUpDto.builder()
                .nickname("테스터2")
                .department("컴퓨터공학과")
                .email("mopil1102@naver.com")
                .id("mopil1102")
                .userName("테스터2")
                .password("tester1234")
                .passwordCheck("tester1234")
                .phoneNumber("010-1234-1234")
                .signUpToken("ASDFASDF")
                .schoolId(60171442)
                .build();

        // 중복 이메일이면 예외가 발생함 (발생해야지 테스트 성공)
        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> {
            Member newMember1 = signUpService.signUp(form1);
            Member newMember2 = signUpService.signUp(form2);
        });
    }

    @Test
    @Rollback
    void fail_password_mismatch() {
        SignUpDto form = SignUpDto.builder()
                .nickname("테스터2")
                .department("컴퓨터공학과")
                .email("mopil1102@naver.com")
                .id("mopil1102")
                .userName("테스터2")
                .password("tester1234")
                .passwordCheck("1111")
                .phoneNumber("010-1234-1234")
                .signUpToken("ASDFASDF")
                .schoolId(60171442)
                .build();

        // 비밀번호 확인이 다르기 때문에 예외가 발생해야함
        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> {
            Member newMember1 = signUpService.signUp(form);
        });
    }
}
