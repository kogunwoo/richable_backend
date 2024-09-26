package com.idle.kb_i_dle_backend.member.service;

import com.idle.kb_i_dle_backend.member.dto.MemberDTO;
import com.idle.kb_i_dle_backend.member.dto.MemberJoinDTO;
import com.idle.kb_i_dle_backend.member.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper, @Lazy PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean checkDupl(String id) {
        // Implement this method using memberMapper
        return memberMapper.checkDupl(id);
    }


    @Override
    @Transactional
    public MemberDTO getMember(String id) {
        // Implement this method using memberMapper
        return memberMapper.getMember(id);
    }

    @Override
    @Transactional
    public void MemberJoin(MemberJoinDTO memberjoindto) {
        if(memberMapper.checkDupl(memberjoindto.getId())){
            throw new IllegalStateException("User already exists");
        }
        if (memberjoindto.getNickname() == null || memberjoindto.getNickname().length() > 50) {
            throw new IllegalArgumentException("Nickname must not be null and should not exceed 50 characters");
        }
        // Perform necessary checks and add try-catch blocks for any exceptions.
        if (memberjoindto.getId() == null || memberjoindto.getId().isEmpty()) {
            throw new IllegalStateException("User ID is required");
        }
        String encodePassword = passwordEncoder.encode(memberjoindto.getPassword());

        MemberJoinDTO newUser = new MemberJoinDTO(
                memberjoindto.getId(),
                encodePassword,
                memberjoindto.getNickname(),
                memberjoindto.getGender(),
                memberjoindto.getEmail(),
                memberjoindto.getBirth_year()
        );

        System.out.println("Inserting new user: " + newUser); // 디버깅을 위해 추가

        memberMapper.insertNewMember(newUser);
    }

    @Override
    public MemberDTO findById(String id) {
        // This method is used for JWT authentication
        return memberMapper.findById(id);
    }

    @Override
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return false;
    }

    @Override
    public boolean checkAgree(boolean info, boolean finance, String id) {
        try {
            this.memberMapper.updateAgree(info, finance, id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

}