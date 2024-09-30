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
@Table(name = "bank", catalog = "asset")
@Getter
public class UserBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User uid;

    @Column(name = "org_code")
    private String orgCode;

    @Column(name = "account_num")
    private Long accountNum;

    @Column(name = "prod_name")
    private String name;

    @NotNull
    @Column(name = "prod_category")
    private String category;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "currency_code")
    private String currencyCode;

    @NotNull
    @Column(name = "balance_amt")
    private Long balanceAmt;

    @NotNull
    @Column(name = "add_date")
    private Date addDate;

    @Column(name = "delete_date")
    private Date deleteDate;
}
