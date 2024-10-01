package com.idle.kb_i_dle_backend.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResultDTO {
    private String token;
    private UserInfoDTO userInfo;
}