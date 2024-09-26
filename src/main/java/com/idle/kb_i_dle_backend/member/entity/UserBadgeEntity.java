package com.idle.kb_i_dle_backend.member.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_badge")
public class UserBadgeEntity {

    @EmbeddedId
    private UserBadgeId id;  // 복합 키 사용

    @Column(name = "badge", length = 100, nullable = false)
    private String badge;  // badge 컬럼 매핑

    @Column(name = "badge_achive", columnDefinition = "TINYINT(1)", nullable = false)
    private boolean badgeAchive;  // badge_achive 컬럼 매핑

    @Column(name = "badge_main", columnDefinition = "TINYINT(1)", nullable = false)
    private boolean badgeMain;  // badge_main 컬럼 매핑

    // 기본 생성자
    public UserBadgeEntity() {}
}
