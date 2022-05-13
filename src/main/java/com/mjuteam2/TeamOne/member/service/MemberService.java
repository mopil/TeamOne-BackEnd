package com.mjuteam2.TeamOne.member.service;

import com.mjuteam2.TeamOne.member.config.EncryptManager;
import com.mjuteam2.TeamOne.member.config.SessionManager;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.dto.FindMemberForm;
import com.mjuteam2.TeamOne.member.dto.MemberSessionResponse;
import com.mjuteam2.TeamOne.member.dto.ResetPasswordForm;
import com.mjuteam2.TeamOne.member.dto.SignInForm;
import com.mjuteam2.TeamOne.member.exception.FindFormException;
import com.mjuteam2.TeamOne.member.exception.MemberException;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.mjuteam2.TeamOne.member.config.EncryptManager.check;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;
    private final EmailService emailService;

    /**
     * 로그인
     */
    // 로그인 확인
    public Member getLoginMember(HttpServletRequest request) throws LoginException {
        request.getHeaderNames().asIterator().forEachRemaining(name -> log.info("헤더 값 # {} = {}", name, request.getHeader(name)));
        String sessionId = request.getHeader(SessionManager.SESSION_ID);
        log.info("클라이언트로 부터 요청받은 쿠키(세션값) = {}", sessionId);
        return sessionManager.getLoginMember(sessionId);
    }

    public MemberSessionResponse login(SignInForm form, HttpServletRequest request) throws LoginException {
        Member loginMember = memberRepository.findByUserId(form.getUserId())
                .orElseThrow(() -> new MemberException("회원을 찾을 수 없음."));

        if (!check(form.getPassword(), loginMember.getPassword())) {
            throw new LoginException("비밀번호가 맞지 않습니다.");
        }

        // 로그인 성공시
        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성 (여기서는 그냥 랜덤값을 생성하는 용도로만 사용함)
        HttpSession session = request.getSession();
        log.info("방금 로그인 처리할 새로생성된 회원의 세션값 = {}", session.getId());

        // 세션매니저에 로그인 회원정보 보관
        // 주의 : 포스트맨 연동을 위해서 무조건 Prefix(JESSIONID=)를 붙혀줘야함
        sessionManager.save(SessionManager.PREFIX + session.getId(), loginMember);

        return new MemberSessionResponse(loginMember.toResponse(), SessionManager.PREFIX + session.getId());
    }

    /**
     * 로그아웃
     */
    public void logout(HttpServletRequest request) {
        // 세션을 삭제한다.
        sessionManager.expire(request.getHeader(SessionManager.SESSION_ID));
    }

    /**
     * 아이디 찾기
     */
    public Member findByUserId(FindMemberForm form) {
        Member findMember = memberRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new MemberException("회원을 찾을 수 없음."));

        if (!Objects.equals(findMember.getSchoolId(), form.getSchoolId())) {
            throw new FindFormException("학번이 올바르지 않습니다.");
        }

        return findMember;
    }

    /**
     * 비밀번호 재설정
     */
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

    /**
     * 회원 하나 조회
     */
    @Transactional(readOnly = true)
    public Member findByUserId(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() ->  new MemberException("해당 회원이 없습니다."));
    }

    /**
     * 회원 정보 수정
     */
    // 닉네임 변경
    public void updateNickname(Member loginMember, String newNickname) {
        loginMember.updateNickname(newNickname);
    }

    // 비밀번호 변경
    public void updatePassword(Member loginMember, String newPassword) {
        loginMember.updatePassword(EncryptManager.hash(newPassword));
    }

    // 자기소개 변경
    public void updateIntroduce(Member loginMember, String newIntroduce) {
        loginMember.updateIntroduce(newIntroduce);
    }

    // 포인트 변경
    public void addPoint(Member loginMember, int amount) {
        loginMember.addPoint(amount);
    }

    /**
     * 회원 탈퇴
     */
    public void deleteMember(Long id, HttpServletRequest request) {
        memberRepository.deleteById(id);
        logout(request);
    }
}
