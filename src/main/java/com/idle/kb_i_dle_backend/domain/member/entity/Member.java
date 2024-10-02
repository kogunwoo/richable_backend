package com.idle.kb_i_dle_backend.domain.member.entity;

import com.idle.kb_i_dle_backend.domain.finance.entity.Spot;
import javax.validation.constraints.NotNull;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user_info", catalog = "user_info")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    @NotNull
    @Column(length = 50)
    private String id;

    @NotNull
    @Column(length = 100)
    private String password;

    @NotNull
    @Column(length = 100)
    private String email;

    @Column(length = 10)
    private String social;

    @NotNull
    private Integer birth_year;

    @Column(length = 10)
    private String gender;

    @Column(length = 255)
    private String profile;

    @Column(name = "agreement_info")
    private Boolean agreementInfo;

    @Column(name = "agreement_finance")
    private Boolean agreementFinance;

    @Column(name = "is_mentor")
    @Builder.Default
    private Boolean isMentor=false;

    @Column(name = "is_certification")
    @Builder.Default
    private Boolean isCertification=false;

    @Column(length = 100)
    private String nickname;

    @Column(length = 20)
    private String auth;

    // User와 Spot의 양방향 관계 설정
    @OneToMany(mappedBy = "uid", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Spot> spots;
    // User와 UserApi의 관계 설정
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private MemberAPI memberAPI;

}
