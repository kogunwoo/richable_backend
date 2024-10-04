package com.idle.kb_i_dle_backend.domain.finance.entity;

import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "asset_summary", catalog ="asset")
@Getter
public class AssetSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member uid;

    private BigInteger bond;

    private BigInteger deposit;

    private BigInteger saving;

    private BigInteger subscription;

    private BigInteger withdrawal;

    private BigInteger cash;

    private BigInteger stock;

    private BigInteger coin;

    @Column(name = "total_amount")
    private BigInteger totalAmount;

    @Column(name = "update_date")
    private Date updateDate;

}
