package com.mjuteam2.TeamOne;

import com.mjuteam2.TeamOne.member.Member;
import com.mjuteam2.TeamOne.member.MemberType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component @Slf4j
public class InitDummyData {

//    @PostConstruct
//    private void init() {
//        Member member = Member.builder()
//                .userId("mopil1102")
//                .password("1234")
//                .userName("배성흥")
//                .email("mopil1102@naver.com")
//                .memberType(MemberType.USER)
//                .nickname("모필")
//                .build();
//        log.info("더미데이터 세팅 완료 {}", member);
//    }
}
