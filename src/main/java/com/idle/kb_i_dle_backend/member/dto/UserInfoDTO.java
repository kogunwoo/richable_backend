package com.idle.kb_i_dle_backend.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {
    private String nickname;
    private String email;
    private String img;
    private String birthYear;
    private String gender;
    private boolean certification;
    private UserApiDTO api;
}
