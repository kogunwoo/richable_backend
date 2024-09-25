package com.idle.kb_i_dle_backend.member.service;

import com.idle.kb_i_dle_backend.member.dto.CustomUser;
import com.idle.kb_i_dle_backend.member.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberService memberService;

    @Autowired
    public CustomUserDetailsService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberDTO member = memberService.getMember(username);
        if (member == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return CustomUser.from(member);
    }
}