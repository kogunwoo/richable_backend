package com.idle.kb_i_dle_backend.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberJoinDTO {
    String id;
    String password;
    String nickname;
    char gender;
    String email;
    String birth_year;
}
