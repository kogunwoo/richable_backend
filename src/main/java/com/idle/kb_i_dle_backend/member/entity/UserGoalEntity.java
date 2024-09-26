package com.idle.kb_i_dle_backend.member.entity;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
@Getter
@Setter
@Entity
@Table(name = "user_goal")
public class UserGoalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int index;

    @Column(name = "UID", nullable = false)
    private int uid;

    @Column(length = 50, nullable = false)
    private String category;

    @Column(length = 1000, nullable = false)
    private String title;

    @Column(nullable = false)
    private long amount;

    @Column(name = "set_date", nullable = false)
    private Timestamp setDate;

    @Column(nullable = false)
    private int priority;

    @Column(name = "is_achive", columnDefinition = "TINYINT(1)")
    private boolean isAchive;

    // 기본 생성자
    public UserGoalEntity() {}
    }

