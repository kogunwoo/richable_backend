package com.idle.kb_i_dle_backend.member.dto;

import com.idle.kb_i_dle_backend.member.entity.Member;
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
public class CustomMember extends User {
    private MemberDTO member;

//    public CustomUser(MemberDTO member, Collection<? extends GrantedAuthority> authorities) {
//        super(member.getId(), member.getPassword(), authorities);
//        this.member = member;
//    }

    public CustomMember(MemberDTO member) {
        super(member.getId(), member.getPassword(), member.getAuth());
        this.member = member;
    }

    public static CustomMember from(Member member) {
        Collection<GrantedAuthority> authorities = Collections.emptyList();
        if (member.getAuth() != null && !member.getAuth().isEmpty()) {
            authorities = Arrays.stream(member.getAuth().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        return new CustomMember(new MemberDTO(
                member.getUid(),
                member.getId(),
                member.getPassword(),
                member.getEmail(),
                member.getSocial(),
                member.getBirth_year(),
                member.getGender().charAt(0), // Assuming gender is stored as a String
                member.getProfile(),
                member.getAgreementInfo(),
                member.getAgreementFinance(),
                member.getIsMentor(),
                member.getIsCertification(),
                member.getNickname(),
                member.getAuth()
        ));
    }

    // Getter for uid
    public Integer getUid() {
        return member.getUid();
    }
}