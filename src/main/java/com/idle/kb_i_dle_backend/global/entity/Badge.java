package com.idle.kb_i_dle_backend.global.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "badge", catalog = "master")
@Getter
public class Badge {
    @Id
    @Column(name = "badge_no")
    private Integer badgeNo;

    @NotNull
    @Column(length = 100)
    private String badge;

    @NotNull
    private String image;

    private String description;
}
