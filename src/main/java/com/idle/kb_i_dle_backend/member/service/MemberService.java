package com.idle.kb_i_dle_backend.member.service;

import com.idle.kb_i_dle_backend.member.dto.MemberDTO;
import com.idle.kb_i_dle_backend.member.dto.MemberJoinDTO;

public interface MemberService {
    boolean checkDupl(String id);

    MemberDTO getMember(String id);

    void MemberJoin(MemberJoinDTO memberjoindto);

    // Add this method if it doesn't exist
    MemberDTO findById(String id);

    boolean checkPassword(String rawPassword, String encodedPassword);
}
