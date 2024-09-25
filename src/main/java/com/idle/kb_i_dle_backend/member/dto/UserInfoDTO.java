package com.idle.kb_i_dle_backend.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDTO {
    private Integer uid;
    private String id;
    private String nickname;
    private String auth;

    public static UserInfoDTO of(MemberDTO member) {
        return new UserInfoDTO(member.getUid() ,member.getId(), member.getNickname(), member.getAuth().toString());
    }
}