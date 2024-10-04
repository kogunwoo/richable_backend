package com.idle.kb_i_dle_backend.domain.finance.entity;

import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coin", catalog = "asset")
public class Coin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`index`")
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member uid;

    @NotNull
    @Column(length = 10)
    private String currency;

    @NotNull
    private Double balance;

    @NotNull
    @Column(name = "avg_buy_price")
    private Double avgBuyPrice;

    @NotNull
    @Column(name = "unit_currency", length = 10)
    private String unitCurrency;

    @Column(name = "prod_category", length = 100)
    private String category;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "add_date")
    private Date addDate;

    @Column(name = "delete_date")
    private Date deleteDate;

    // 엔티티가 처음 영속화될 때(addDate를 자동으로 설정)
    @PrePersist
    protected void onCreate() {
        this.addDate = new Date();  // 현재 시간을 자동으로 설정
    }
}
