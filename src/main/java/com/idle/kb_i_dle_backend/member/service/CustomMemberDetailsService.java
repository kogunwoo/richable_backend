package com.idle.kb_i_dle_backend.member.service;

import com.idle.kb_i_dle_backend.member.dto.CustomMember;
import com.idle.kb_i_dle_backend.member.entity.Member;
import com.idle.kb_i_dle_backend.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public CustomMemberDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository; // Inject UserRepository
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findById(username); // Fetch user directly from UserRepository
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return CustomMember.from(member); // Map User entity to CustomUser
    }
}