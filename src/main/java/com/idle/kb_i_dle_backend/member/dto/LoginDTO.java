package com.idle.kb_i_dle_backend.member.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private String id;
    private String password;

    public static LoginDTO of(HttpServletRequest request) {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.readValue(request.getInputStream(), LoginDTO.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse login request", e);
        }
    }
}