package com.idle.kb_i_dle_backend.domain.finance.entity;

import com.idle.kb_i_dle_backend.domain.member.entity.User;
import java.util.Date;
import javax.persistence.*;
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

    @Column(name = "org_code", length = 100)
    private String orgCode;

    @Column(name = "account_num")
    private Long accountNum;

    @Column(name = "prod_name",length = 100)
    private String name;

    @NotNull
    @Column(name = "prod_category", length = 100)
    private String category;

    @Column(name = "account_type", length = 100)
    private String accountType;

    @Column(name = "currency_code", length = 10)
    private String currencyCode;

    @NotNull
    @Column(name = "balance_amt")
    private Long balanceAmt;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "add_date")
    private Date addDate;

    @Column(name = "delete_date")
    private Date deleteDate;
}
