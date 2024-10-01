package com.idle.kb_i_dle_backend.domain.member.dto;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import java.util.stream.Collectors;
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

    public static CustomUser from(com.idle.kb_i_dle_backend.domain.member.entity.User user) {
        Collection<GrantedAuthority> authorities = Collections.emptyList();
        if (user.getAuth() != null && !user.getAuth().isEmpty()) {
            authorities = Arrays.stream(user.getAuth().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        return new CustomUser(new MemberDTO(
                user.getUid(),
                user.getId(),
                user.getPassword(),
                user.getEmail(),
                user.getSocial(),
                user.getBirth_year(),
                user.getGender().charAt(0), // Assuming gender is stored as a String
                user.getProfile(),
                user.getAgreementInfo(),
                user.getAgreementFinance(),
                user.getIsMentor(),
                user.getIsCertification(),
                user.getNickname(),
                user.getAuth()
        ), authorities);
    }

    // Getter for uid
    public Integer getUid() {
        return member.getUid();
    }
}