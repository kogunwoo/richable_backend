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

    public static CustomUser from(com.idle.kb_i_dle_backend.domain.member.entity.Member user) {
        Collection<GrantedAuthority> authorities = Collections.emptyList();
        if (user.getAuth() != null && !user.getAuth().isEmpty()) {
            authorities = Arrays.stream(user.getAuth().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        // MemberDTO 객체를 builder를 사용해 생성
        MemberDTO memberDTO = MemberDTO.builder()
                .uid(user.getUid())
                .id(user.getId())
                .password(user.getPassword())
                .email(user.getEmail())
                .gender(user.getGender().charAt(0))  // String에서 char로 변환
                .birth_year(String.valueOf(user.getBirth_year()))
                .profile(user.getProfile())
                .agreement_info(user.getAgreementInfo())
                .agreement_finance(user.getAgreementFinance())
                .is_mentor(user.getIsMentor())
                .is_certification(user.getIsCertification())
                .nickname(user.getNickname())
                .auth(user.getAuth())
                .build();

        return new CustomUser(memberDTO, authorities);
    }
    // Getter for uid
    public Integer getUid() {
        return member.getUid();
    }
}
