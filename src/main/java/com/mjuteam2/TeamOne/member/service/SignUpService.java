package com.mjuteam2.TeamOne.member.service;


import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import com.mjuteam2.TeamOne.member.dto.SignUpDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
public class SignUpService {

    private final MemberRepository memberRepository;
    private Map<String, String> authTokenStorage = new ConcurrentHashMap<>();

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

    /**
     * 인증번호 확인
     */
    public boolean authTokenCheck(String email, String compareToken) {
        if (authTokenStorage.containsKey(email)) {
            return authTokenStorage.get(email).equals(compareToken);
        }
        return false;
    }

    public String setAuthToken(String email) {
        String authToken = generateToken();
        authTokenStorage.put(email, authToken);
        return authToken;
    }

    public String getAuthToken(String email) {
        return authTokenStorage.get(email);
    }

    private String generateToken() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }

    public boolean nicknameCheck(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public boolean idCheck(String id) {
        return memberRepository.existsByUserId(id);
    }
}
