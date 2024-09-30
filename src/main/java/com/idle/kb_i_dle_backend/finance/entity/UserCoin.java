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
@Table(name = "coin", catalog = "asset")
@Getter
public class UserCoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User uid;

    @NotNull
    private String currency;

    @NotNull
    private Double balance;

    @NotNull
    @Column(name = "avg_buy_price")
    private Double avgBuyPrice;

    @NotNull
    @Column(name = "unit_currency")
    private String curreny;

    @Column(name = "prod_category")
    private String category;

    @NotNull
    @Column(name = "add_date")
    private Date addDate;

    @Column(name = "delete_date")
    private Date deleteDate;
}
