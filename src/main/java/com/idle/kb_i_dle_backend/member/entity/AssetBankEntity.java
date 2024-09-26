package com.idle.kb_i_dle_backend.member.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "bank")
public class AssetBankEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int index; // Primary Key

    @Column(name = "uid", nullable = false)
    private int uid;

    @Column(name = "org_code", length = 100, nullable = false)
    private String orgCode;

    @Column(name = "account_num", nullable = false)
    private long accountNum;

    @Column(name = "prod_name", length = 100, nullable = false)
    private String prodName;

    @Column(name = "prod_category", length = 100, nullable = false)
    private String prodCategory;

    @Column(name = "account_type", length = 100, nullable = false)
    private String accountType;

    @Column(name = "currency_code", length = 10, nullable = false)
    private String currencyCode;

    @Column(name = "balance_amt", nullable = false)
    private long balanceAmt;

    @Column(name = "add_date", nullable = false)
    private Timestamp addDate;

    @Column(name = "delete_date")
    private Date deleteDate;

    // 기본 생성자와 Lombok으로 getter와 setter가 자동 생성됩니다.
}
