package com.idle.kb_i_dle_backend.member.dto;

import java.util.Collection;
import java.util.Collections;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
public class CustomUser extends User {
    private MemberDTO member;

    public CustomUser(MemberDTO member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getId(), member.getPassword(), authorities);
        this.member = member;
    }

    // Static factory method to create CustomUser from MemberDTO
    public static CustomUser from(com.idle.kb_i_dle_backend.member.entity.User user) {
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getAuth().toString()));
        return new CustomUser(new MemberDTO(
                user.getUid(),
                user.getId(),
                user.getPassword(),
                user.getEmail(),
                user.getSocial(),
                user.getBirth_year(),
                user.getGender().charAt(0), // Assuming gender is stored as a String
                user.getProfile(),
                user.isAgreement_info(),
                user.isAgreement_finace(),
                user.isMentor(),
                user.isCertification(),
                user.getNickname(),
                user.getAuth()
        ), authorities);
    }

    // Getter for uid
    public Integer getUid() {
        return member.getUid();
    }
}