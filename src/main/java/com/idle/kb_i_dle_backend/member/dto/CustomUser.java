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
    public static CustomUser from(MemberDTO member) {
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(member.getAuth().toString()));
        return new CustomUser(member, authorities);
    }

    // Getter for uid
    public Integer getUid() {
        return member.getUid();
    }
}