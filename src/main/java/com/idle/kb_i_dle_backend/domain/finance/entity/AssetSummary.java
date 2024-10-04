package com.idle.kb_i_dle_backend.domain.finance.entity;

import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import java.math.BigDecimal;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "asset_summary", catalog = "asset")
@Getter
public class AssetSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member uid;

    private BigDecimal bond;
    private BigDecimal deposit;
    private BigDecimal saving;
    private BigDecimal subscription;
    private BigDecimal withdrawal;
    private BigDecimal cash;
    private BigDecimal stock;
    private BigDecimal coin;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "update_date")
    private Date updateDate;

    // 엔티티가 처음 영속화될 때(Date를 자동으로 설정)
    @PrePersist
    protected void onCreate() {
        this.updateDate = new Date();  // 현재 시간을 자동으로 설정
    }
}
