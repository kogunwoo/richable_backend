package com.idle.kb_i_dle_backend.member.service;

import com.idle.kb_i_dle_backend.member.dto.MemberDTO;
import com.idle.kb_i_dle_backend.member.dto.MemberJoinDTO;
import com.idle.kb_i_dle_backend.member.mapper.MemberMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

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
    public MemberJoinDTO getMemberJoin(String id) {
        // Implement this method using memberMapper
        return memberMapper.getMemberJoin(id);
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

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

}