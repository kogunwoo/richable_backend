package com.idle.kb_i_dle_backend.consume.entity;

import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "outcome_average",catalog = "outcome")
public class OutcomeAverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int index;

    @Column(name = "household_head_age_group")  // 실제 테이블의 컬럼과 매핑
    private String ageGroup;

    @Column(name = "outcome_expenditure_category")  // 실제 테이블의 컬럼과 매핑
    private String category;

    @Column(name = "outcome")
    private Integer outcome;

    @Column(name = "household_size", precision = 3, scale = 2)
    private BigDecimal householdSize;

    @Column(name = "quater")
    private String quater;

    // getter, setter, constructor 생략
}
