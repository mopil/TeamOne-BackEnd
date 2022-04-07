package com.mjuteam2.TeamOne.member;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpService {

    private final MemberRepository memberRepository;

    public Member signUp(SignUpDto form) throws Exception {
        if (memberRepository.existsByEmail(form.getEmail())) {
            throw new Exception("이미 가입한 이메일입니다.");
        }
        if (!form.passwordCheck()) {
            throw new Exception("비밀번호를 다시 확인해주세요.");
        }
        Member newMember = form.toMember();
        memberRepository.save(newMember);
        log.info("new member signed up = {}", newMember);
        return newMember;
    }

    public boolean nicknameCheck(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public boolean idCheck(String id) {
        return memberRepository.existsByUserId(id);
    }
}
