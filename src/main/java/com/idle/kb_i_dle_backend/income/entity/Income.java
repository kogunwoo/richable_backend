package com.idle.kb_i_dle_backend.income.entity;

import com.idle.kb_i_dle_backend.member.entity.Member;
import java.util.Date;
import javax.persistence.*;

import lombok.Getter;

@Entity
@Getter
@Table(name = "income" , catalog = "asset")
public class Income {
    @Id
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member uid;

    @Column(length = 100)
    private String type;
    private Long amount;
    private Date date;
    private String descript;
    private String memo;
}
