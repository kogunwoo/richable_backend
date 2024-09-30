package com.idle.kb_i_dle_backend.member.service;

import com.idle.kb_i_dle_backend.member.dto.MemberDTO;
import com.idle.kb_i_dle_backend.member.dto.MemberJoinDTO;
import com.idle.kb_i_dle_backend.member.entity.Member;

public interface MemberService {
    boolean checkDupl(String id);

    Member getMember(String id);

    void MemberJoin(MemberJoinDTO memberjoindto);

    // Add this method if it doesn't exist
    MemberDTO findById(String id);

    boolean checkPassword(String rawPassword, String encodedPassword);

    boolean checkAgree(boolean info, boolean finance,String id);

    String findIdByEmail(String email);

    String generateAndSaveVerificationCode(String email);

    boolean verifyCode(String email, String code);

    boolean resetPassword(String id, String newPassword);

    boolean checkEmailExists(String email);

    MemberDTO findByEmail(String email);
}
