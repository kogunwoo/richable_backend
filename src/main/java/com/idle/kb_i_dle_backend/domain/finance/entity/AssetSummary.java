package com.idle.kb_i_dle_backend.domain.finance.entity;

import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "asset_summary", catalog = "asset")
@Getter
public class AssetSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private Member uid;

    @Column(name = "bond")
    private BigInteger bond;

    @Column(name = "deposit")
    private BigInteger deposit;

    @Column(name = "saving")
    private BigInteger saving;

    @Column(name = "subscription")
    private BigInteger subscription;

    @Column(name = "withdrawal")
    private BigInteger withdrawal;

    @Column(name = "cash")
    private BigInteger cash;

    @Column(name = "stock")
    private BigInteger stock;

    @Column(name = "coin")
    private BigInteger coin;

    @Column(name = "total_amount")
    private BigInteger totalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;
}

