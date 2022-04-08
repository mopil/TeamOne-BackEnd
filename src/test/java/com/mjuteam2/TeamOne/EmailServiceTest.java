package com.mjuteam2.TeamOne;

import com.mjuteam2.TeamOne.member.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    EmailService emailService;

    @Test
    void authTokenCheck() {
        // given
        String tokenInList = "abcdef";
        String testToken = "abcdef";

    }
}
