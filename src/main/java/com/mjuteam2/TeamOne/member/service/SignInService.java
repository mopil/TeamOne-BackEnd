package com.mjuteam2.TeamOne.member.service;

import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.dto.FindMemberForm;
import com.mjuteam2.TeamOne.member.dto.SignInForm;
import com.mjuteam2.TeamOne.member.exception.FindFormException;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.Optional;

import static com.mjuteam2.TeamOne.common.EncryptManager.check;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
public class SignInService {

    private final MemberRepository memberRepository;

    public Member Login(SignInForm form) throws LoginException {
        Optional<Member> loginMember = memberRepository.findByUserId(form.getId());

        if (!loginMember.isPresent()) {
            throw new LoginException("계정이 존재하지 않습니다");
        }

        else if (!check(form.getPassword(), loginMember.get().getPassword())) {
            throw new LoginException("비밀번호가 맞지 않습니다");
        }

        return loginMember.get();
    }

    public Member FindByUserId(FindMemberForm form) {
        Optional<Member> FindMember = memberRepository.findByEmail(form.getEmail());

        if (!FindMember.isPresent()) {
            throw new FindFormException("계정이 존재하지 않습니다");
        }

        else if (FindMember.get().getSchoolId() != form.getSchoolId()) {
            throw new FindFormException("학번이 올바르지 않습니다");
        }

        return FindMember.get();
    }
}
