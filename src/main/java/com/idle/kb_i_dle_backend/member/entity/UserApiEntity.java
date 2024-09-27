package com.idle.kb_i_dle_backend.member.entity;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
//@Entity
//@Table(name = "user_api")
public class UserApiEntity {

    @Id
    @Column(name = "uid", nullable = false)
    private int uid;

    @Column(name = "api_bank", length = 255)
    private String apiBank;

    @Column(name = "api_stock", length = 255)
    private String apiStock;

    @Column(name = "api_stock_token", length = 255)
    private String apiStockToken;

    @Column(name = "api_stock_secret", length = 255)
    private String apiStockSecret;

    @Column(name = "api_stock_app", length = 255)
    private String apiStockApp;

    @Column(name = "api_coin", length = 255)
    private String apiCoin;

    @Column(name = "api_coin_app", length = 255)
    private String apiCoinApp;

    @Column(name = "api_coin_secret", length = 255)
    private String apiCoinSecret;

    // 기본 생성자
    public UserApiEntity() {}
}
