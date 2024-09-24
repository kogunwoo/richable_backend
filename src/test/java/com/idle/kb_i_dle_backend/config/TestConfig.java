package com.idle.kb_i_dle_backend.config;

import com.idle.kb_i_dle_backend.member.mapper.MemberMapper;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Import({SecurityConfig.class})
public class TestConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MemberMapper memberMapper() {
        return Mockito.mock(MemberMapper.class);
    }
}