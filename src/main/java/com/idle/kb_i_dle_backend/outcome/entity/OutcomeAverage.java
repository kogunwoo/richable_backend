package com.idle.kb_i_dle_backend.outcome.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "outcome_average",catalog = "outcome")
public class OutcomeAverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int index;

    @NotNull
    @Column(name = "household_head_age_group", length = 50)  // 실제 테이블의 컬럼과 매핑
    private String ageGroup;

    @NotNull
    @Column(name = "outcome_expenditure_category", length = 100)  // 실제 테이블의 컬럼과 매핑
    private String category;

    @NotNull
    @Column(name = "outcome")
    private Integer outcome;

    @NotNull
    @Column(name = "household_size", precision = 3, scale = 2)
    private BigDecimal householdSize;

    @Column(name = "quater", length = 10)
    private String quater;

    // getter, setter, constructor 생략
}
