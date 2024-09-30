package com.idle.kb_i_dle_backend.member.service;

import com.idle.kb_i_dle_backend.member.dto.CustomUser;
import com.idle.kb_i_dle_backend.member.entity.User;
import com.idle.kb_i_dle_backend.member.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository; // Inject UserRepository
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username); // Fetch user directly from UserRepository
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return CustomUser.from(user); // Map User entity to CustomUser
    }
}