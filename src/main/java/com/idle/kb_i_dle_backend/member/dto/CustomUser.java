package com.idle.kb_i_dle_backend.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustomUser extends User {
    private MemberDTO member;

    // Constructor that only takes username, password, and authorities
    public CustomUser(String username, String password,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    // Constructor that takes MemberDTO and initializes CustomUser
    public CustomUser(MemberDTO member) {
        super(member.getId(), member.getPassword(), member.getAuth());
        this.member = member;
    }
}