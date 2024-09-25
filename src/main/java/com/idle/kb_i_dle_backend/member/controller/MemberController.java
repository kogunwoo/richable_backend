package com.idle.kb_i_dle_backend.member.controller;

import com.idle.kb_i_dle_backend.member.dto.*;
import com.idle.kb_i_dle_backend.member.util.JwtProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class MemberController {

    private final AuthenticationManager authenticationManager;
    private final JwtProcessor jwtProcessor;

    public MemberController(AuthenticationManager authenticationManager, JwtProcessor jwtProcessor) {
        this.authenticationManager = authenticationManager;
        this.jwtProcessor = jwtProcessor;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        // Perform authentication
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getId(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String jwtToken = jwtProcessor.generateToken(loginDTO.getId());

        // Assuming you have a method to fetch UserInfoDTO (e.g., from the authenticated user details)
        UserInfoDTO userInfo = getUserInfoFromAuthentication(authentication);

        // Return the AuthResultDTO with token and user info
        return ResponseEntity.ok(new AuthResultDTO(jwtToken, userInfo));
    }

    // Helper method to retrieve user information from the authentication object
    private UserInfoDTO getUserInfoFromAuthentication(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        MemberDTO member = customUser.getMember();  // Retrieve the MemberDTO object
        return new UserInfoDTO(member.getUid(), member.getEmail(), member.getAuth().toString());
    }
}
