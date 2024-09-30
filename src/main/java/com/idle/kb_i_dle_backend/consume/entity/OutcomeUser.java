package com.idle.kb_i_dle_backend.consume.entity;

import com.idle.kb_i_dle_backend.member.entity.User;
import lombok.Getter;
import lombok.ToString;
import org.apache.ibatis.annotations.Many;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@ToString
@Table(name = "outcome_user",catalog = "outcome")
public class OutcomeUser {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int index;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User uid;

    @NotNull
    @Column(name = "outcome_expenditure_category")  // 실제 테이블의 컬럼과 매핑
    private String category;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @NotNull
    private long amount;

    private String descript;

    private String memo;
}
