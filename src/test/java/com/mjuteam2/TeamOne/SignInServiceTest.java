package com.mjuteam2.TeamOne;

import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.dto.FindMemberForm;
import com.mjuteam2.TeamOne.member.dto.SignInForm;
import com.mjuteam2.TeamOne.member.dto.SignUpForm;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import com.mjuteam2.TeamOne.member.service.SignInService;
import com.mjuteam2.TeamOne.member.service.SignUpService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static com.mjuteam2.TeamOne.common.EncryptManager.check;

@SpringBootTest
@Transactional
class SignInServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    SignInService signInService;

    @Autowired
    SignUpService signUpService;

    @Test
    @Rollback(value = false)
    void login_Success() throws Exception {

        SignUpForm signUpForm1 = SignUpForm.builder()
                .nickname("12341234124")
                .department("컴퓨터공학과")
                .email("login@naver.com")
                .id("userid1")
                .userName("테스터1")
                .password("123123123")
                .passwordCheck("123123123")
                .phoneNumber("010-1234-1234")
                .authToken("sadfsf")
                .schoolId("4123412342")
                .build();

        Member member1 = signUpService.signUp(signUpForm1);

        SignInForm loginForm1 = SignInForm.builder()
                .id("userid1")
                .password("123123123")
                .build();

        signInService.Login(loginForm1);

        Assertions.assertEquals(true, check(loginForm1.getPassword(), member1.getPassword()));
    }


    @Test
    @Rollback(value = false)
    void login_failId() throws Exception {

        SignUpForm signUpForm1 = SignUpForm.builder()
                .nickname("테스터1")
                .department("컴퓨터공학과")
                .email("mopil1103@naver.com")
                .id("userid2")
                .userName("테스터1")
                .password("123123123")
                .passwordCheck("123123123")
                .phoneNumber("010-1234-1234")
                .authToken("ASDFASDF")
                .schoolId("60171442")
                .build();

        Member member1 = signUpService.signUp(signUpForm1);

        SignInForm loginForm1 = SignInForm.builder()
                .id("userid123")
                .password("123123123")
                .build();

        //signInService.Login(loginForm1);

        Assertions.assertNotEquals(loginForm1.getId(), member1.getUserId());
    }

    @Test
    @Rollback(value = false)
    void login_failPassword() throws Exception {

        SignUpForm signUpForm1 = SignUpForm.builder()
                .nickname("테스터1")
                .department("컴퓨터공학과")
                .email("mopil1104@naver.com")
                .id("userid3")
                .userName("테스터1")
                .password("123123123")
                .passwordCheck("123123123")
                .phoneNumber("010-1234-1234")
                .authToken("ASDFASDF")
                .schoolId("60171442")
                .build();

        Member member1 = signUpService.signUp(signUpForm1);

        SignInForm loginForm1 = SignInForm.builder()
                .id("userid1")
                .password("12312312333")
                .build();

        //signInService.Login(loginForm1);

        Assertions.assertEquals(false, check(loginForm1.getPassword(), member1.getPassword()));
    }

    @Test
    @Rollback(value = false)
    void findMember_Success() throws Exception {

        SignUpForm signUpForm1 = SignUpForm.builder()
                .nickname("테스터1")
                .department("컴퓨터공학과")
                .email("mopil1105@naver.com")
                .id("userid4")
                .userName("테스터1")
                .password("123123123")
                .passwordCheck("123123123")
                .phoneNumber("010-1234-1234")
                .authToken("ASDFASDF")
                .schoolId("60171442")
                .build();

        Member member1 = signUpService.signUp(signUpForm1);


        FindMemberForm findForm1 = FindMemberForm.builder()
                .email("mopil1105@naver.com")
                .schoolId("60171442")
                .build();

        Member findMember = signInService.FindByUserId(findForm1);
        Assertions.assertEquals(findMember.getUserId(), member1.getUserId());
    }

    @Test
    @Rollback(value = false)
    void findMember_FailEmail() throws Exception {

        SignUpForm signUpForm1 = SignUpForm.builder()
                .nickname("테스터1")
                .department("컴퓨터공학과")
                .email("mopil1106@naver.com")
                .id("userid5")
                .userName("테스터1")
                .password("123123123")
                .passwordCheck("123123123")
                .phoneNumber("010-1234-1234")
                .authToken("ASDFASDF")
                .schoolId("60171442")
                .build();

        Member member1 = signUpService.signUp(signUpForm1);


        FindMemberForm findForm1 = FindMemberForm.builder()
                .email("mopil1113204@naver.com")
                .schoolId("60171442")
                .build();

        //signInService.FindByUserId(findForm1);
        Assertions.assertNotEquals(findForm1.getEmail(), member1.getEmail());
    }

    @Test
    @Rollback(value = false)
    void findMember_FailSchoolId() throws Exception {

        SignUpForm signUpForm1 = SignUpForm.builder()
                .nickname("테스터1")
                .department("컴퓨터공학과")
                .email("mopil1107@naver.com")
                .id("userid6")
                .userName("테스터1")
                .password("123123123")
                .passwordCheck("123123123")
                .phoneNumber("010-1234-1234")
                .authToken("ASDFASDF")
                .schoolId("60171442")
                .build();

        Member member1 = signUpService.signUp(signUpForm1);


        FindMemberForm findForm1 = FindMemberForm.builder()
                .email("mopil1107@naver.com")
                .schoolId("60171231442")
                .build();

//        signInService.FindByUserId(findForm1);
        Assertions.assertNotEquals(findForm1.getSchoolId(), member1.getSchoolId());
    }
}