package com.idle.kb_i_dle_backend.domain.member.entity;

import java.io.Serializable;
import lombok.Getter;

import javax.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "user_api", catalog = "user_info")
@Getter
@Setter
public class MemberAPI implements Serializable {
    @Id
    private Integer uid;  // Member의 ID와 동일한 타입으로 설정

    @OneToOne
    @MapsId
    @JoinColumn(name = "uid")
    private Member member;

    @Column(name = "api_bank")
    private String Bank;

    @Column(name = "api_stock")
    private String Stock;

    @Column(name = "api_stock_secret")
    private String StockSecret;

    @Column(name = "api_stock_token")
    private String StockToken;

    @Column(name = "api_stock_app")
    private String StockApp;

    @Column(name = "api_coin")
    private String coin;

    @Column(name = "api_coin_app")
    private String coinApp;

    @Column(name = "api_coin_secret")
    private String coinSecret;



}
