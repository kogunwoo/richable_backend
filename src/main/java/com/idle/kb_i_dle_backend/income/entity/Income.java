package com.idle.kb_i_dle_backend.income.entity;

import com.idle.kb_i_dle_backend.member.entity.User;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "income" , catalog = "asset")
public class Income {
    @Id
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User uid;

    private String type;
    private Long amount;
    private Date date;
    private String descript;
    private String memo;
}
