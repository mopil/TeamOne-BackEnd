package com.mjuteam2.TeamOne.member.service;

import com.mjuteam2.TeamOne.member.config.EncryptManager;
import com.mjuteam2.TeamOne.member.config.SessionConst;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.dto.FindMemberForm;
import com.mjuteam2.TeamOne.member.dto.MemberListResponse;
import com.mjuteam2.TeamOne.member.dto.ResetPasswordForm;
import com.mjuteam2.TeamOne.member.dto.SignInForm;
import com.mjuteam2.TeamOne.member.exception.FindFormException;
import com.mjuteam2.TeamOne.member.exception.MemberException;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.mjuteam2.TeamOne.member.config.EncryptManager.check;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
public class SignInService {

    private final MemberRepository memberRepository;
    private final EmailService emailService;

    public MemberListResponse login(SignInForm form, HttpServletRequest request) throws LoginException {
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
        System.out.println("session = " + session);
        System.out.println("session = " + session.getId());

        // 세선에 로그인 회원정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember.get());

        List<Member> loginMemberList = new ArrayList<>();
        loginMemberList.add(loginMember.get());

        return new MemberListResponse(loginMemberList, session.getId());
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

    /**
     * 비밀번호 재설정
     */
    @Transactional
    public Member resetPassword(ResetPasswordForm form) {

        // 폼에 적힌 이메일로 디비에서 멤버 조회
        Member findMember = memberRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new MemberException("회원을 찾을 수 없음."));

        // 학번 정보가 일치할 경우 비밀번호 재설정
        if(findMember.getSchoolId().equals(form.getSchoolId())){
            // 조회한 맴버의 이메일 주소로 임시 비밀번호 메일 전송
            String tempPassword = emailService.sendMail(findMember.getEmail());

            // 해당 임시 비밀번호로 해당 맴버의 비밀번호를 암호화해서 디비에 업데이트
            findMember.updatePassword(EncryptManager.hash(tempPassword));
        } else {
            throw new MemberException("학번이 일치하지 않음.");
        }

        // 비밀번호 재설정된 맴버 객체 반환
        return findMember;
    }
}
