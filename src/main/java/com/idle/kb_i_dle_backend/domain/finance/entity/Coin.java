package com.idle.kb_i_dle_backend.domain.finance.entity;

import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Table(name = "coin", catalog = "asset")
@Getter
public class Coin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String curreny;

    @Column(name = "prod_category", length = 100)
    private String category;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "add_date")
    private Date addDate;

    @Column(name = "delete_date")
    private Date deleteDate;
}
