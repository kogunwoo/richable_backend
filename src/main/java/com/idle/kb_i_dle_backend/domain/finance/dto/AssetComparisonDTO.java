package com.idle.kb_i_dle_backend.domain.finance.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@RequiredArgsConstructor
public class AssetComparisonDTO {
    private Integer uid;
    private BigInteger bsAmount;         // 나의 자산
    private BigInteger spotAvgAmount;    // 나이대 평균 자산
    private BigInteger defer;            // 자산 차이
    private BigInteger bond;
    private BigInteger stock;
    private BigInteger coin;
    private BigInteger deposit;
    private BigInteger withdrawal;
    private BigInteger saving;
    private BigInteger subscription;
    private BigInteger cash;

    // 모든 필드를 초기화하는 생성자 추가
    public AssetComparisonDTO(Integer uid, BigInteger bsAmount, BigInteger spotAvgAmount, BigInteger defer,
                              BigInteger bond, BigInteger stock, BigInteger coin,
                              BigInteger deposit, BigInteger withdrawal, BigInteger saving, BigInteger subscription
            , BigInteger cash) {
        this.uid = uid;
        this.bsAmount = bsAmount;
        this.spotAvgAmount = spotAvgAmount;
        this.defer = defer;
        this.bond = bond;
        this.stock = stock;
        this.coin = coin;
        this.deposit = deposit;
        this.withdrawal = withdrawal;
        this.saving = saving;
        this.subscription = subscription;
        this.cash = cash;
    }

}
