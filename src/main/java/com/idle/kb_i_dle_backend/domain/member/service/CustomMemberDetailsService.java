package com.idle.kb_i_dle_backend.domain.member.service;

import com.idle.kb_i_dle_backend.domain.member.dto.CustomUser;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import com.idle.kb_i_dle_backend.domain.member.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomMemberDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomMemberDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository; // Inject UserRepository
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = userRepository.findById(username); // Fetch member directly from UserRepository
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return CustomUser.from(member); // Map User entity to CustomUser
    }
}