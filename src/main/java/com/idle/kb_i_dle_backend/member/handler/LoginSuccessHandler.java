package com.idle.kb_i_dle_backend.member.handler;

import com.idle.kb_i_dle_backend.member.dto.AuthResultDTO;
import com.idle.kb_i_dle_backend.member.dto.CustomUser;
import com.idle.kb_i_dle_backend.member.dto.UserInfoDTO;
import com.idle.kb_i_dle_backend.member.util.JsonResponse;
import com.idle.kb_i_dle_backend.member.util.JwtProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProcessor jwtProcessor;

    private AuthResultDTO makeAuthResult(CustomUser user) {
        String username = user.getUsername();
        // Generate token
        String token = jwtProcessor.generateToken(username);
        // Combine token and user info into AuthResultDTO
        return new AuthResultDTO(token, UserInfoDTO.of(user.getMember()));
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Get the authenticated user
        CustomUser user = (CustomUser) authentication.getPrincipal();
        // Generate the authentication result and send it as JSON
        AuthResultDTO result = makeAuthResult(user);
        JsonResponse.send(response, result);
    }
}