package com.mjuteam2.TeamOne;

import com.mjuteam2.TeamOne.common.EncryptManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EncryptManagerTest {

    @Test
    void hash() {
        String password = "asdf1234";
        String hashed = EncryptManager.hash(password);
        System.out.println(hashed);

        boolean result = EncryptManager.check(password, hashed);
        Assertions.assertThat(result).isEqualTo(true);
    }
}
