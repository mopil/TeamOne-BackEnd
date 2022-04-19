package com.mjuteam2.TeamOne.member.service;

import com.mjuteam2.TeamOne.member.login.SessionConst;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import com.mjuteam2.TeamOne.member.dto.FindMemberForm;
import com.mjuteam2.TeamOne.member.dto.SignInForm;
import com.mjuteam2.TeamOne.member.exception.FindFormException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

import static com.mjuteam2.TeamOne.common.EncryptManager.check;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
public class SignInService {

    private final MemberRepository memberRepository;

    public Member login(SignInForm form, HttpServletRequest request) throws LoginException {
        Optional<Member> loginMember = memberRepository.findByUserId(form.getUserId());

        if (loginMember.isEmpty()) {
            throw new LoginException("계정이 존재하지 않습니다.");
        }

        else if (!check(form.getPassword(), loginMember.get().getPassword())) {
            throw new LoginException("비밀번호가 맞지 않습니다.");
        }

        // 로그인 성공시
        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();

        // 세선에 로그인 회원정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember.get());

        return loginMember.get();
    }

    public void logout(HttpServletRequest request) {
        // 세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    public Member findByUserId(FindMemberForm form) {
        Optional<Member> FindMember = memberRepository.findByEmail(form.getEmail());

        if (FindMember.isEmpty()) {
            throw new FindFormException("계정이 존재하지 않습니다.");
        }

        else if (!Objects.equals(FindMember.get().getSchoolId(), form.getSchoolId())) {
            throw new FindFormException("학번이 올바르지 않습니다.");
        }

        return FindMember.get();
    }

    public boolean loginCheck(Member loginMember) throws LoginException {
        if (loginMember == null) throw new LoginException("로그인 안 됨.");
        return true;
    }
}
