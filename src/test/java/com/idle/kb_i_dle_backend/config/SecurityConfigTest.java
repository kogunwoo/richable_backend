package com.idle.kb_i_dle_backend.config;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Slf4j
@SpringJUnitConfig(TestConfig.class)
class SecurityConfigTest {

    @Autowired
    private PasswordEncoder pwEncoder;

    @Test
    void passwordEncoderTest() {
        String str = "1234*";
        String enStr = pwEncoder.encode(str);
        String enStr2 = pwEncoder.encode(str);

        log.info("Original password: " + str);
        log.info("Encoded password 1: " + enStr);
        log.info("Encoded password 2: " + enStr2);

        assertNotEquals(enStr, enStr2, "Encoded passwords should be different each time");
        assertTrue(pwEncoder.matches(str, enStr), "Original password should match first encoded version");
        assertTrue(pwEncoder.matches(str, enStr2), "Original password should match second encoded version");
        assertTrue(pwEncoder.matches(str, "$2a$10$8lNJC5tjow7c4XsbjmiDPOOSrA02GgfQUKnCdeeA2BSIwCKHdr0Ni"), "Original password should match second encoded version");
    }
}