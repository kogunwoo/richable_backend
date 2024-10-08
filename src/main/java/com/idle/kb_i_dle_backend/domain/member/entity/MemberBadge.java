//package com.idle.kb_i_dle_backend.domain.member.entity;
//
//import com.idle.kb_i_dle_backend.common.entity.Badge;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "user_badge", catalog = "user_info")
//public class MemberBadge {
//
//    @Id
//    @ManyToOne
//    @JoinColumn(name = "uid")
//    private User uid;
//
//    @Id
//    @ManyToOne
//    @JoinColumn(name = "badge_no")
//    private Badge badgeNo;
//
//
//    @Column(length = 100)
//    private String badge;
//
//    @Column(name = "badge_achive")
//    private Boolean achive;
//
//    @Column(name = "badge_main")
//    private Boolean main;
//}
