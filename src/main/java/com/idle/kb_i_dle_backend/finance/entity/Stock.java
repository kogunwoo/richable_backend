package com.idle.kb_i_dle_backend.finance.entity;

import com.idle.kb_i_dle_backend.member.entity.User;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Table(name = "stock", catalog = "asset")
@Getter
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User uid;

    @NotNull
    private Integer pdno;

    @NotNull
    @Column(name = "prdt_name")
    private String prdtName;

    @Column(name = "hldg_qty")
    private Integer hldgQty;

    @Column(name = "prod_category")
    private String category;

    @NotNull
    @Column(name = "add_date")
    private Date addDate;

    @Column(name = "delete_date")
    private Date deleteDate;
}
