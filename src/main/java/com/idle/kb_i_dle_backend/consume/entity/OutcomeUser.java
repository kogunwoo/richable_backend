package com.idle.kb_i_dle_backend.consume.entity;

import lombok.Getter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Table(name = "outcome_user",catalog = "outcome")
public class OutcomeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int index;

    private int uid;

    @Column(name = "outcome_expenditure_category")  // 실제 테이블의 컬럼과 매핑
    private String category;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private long amount;

    private String descript;

    private String memo;
}
