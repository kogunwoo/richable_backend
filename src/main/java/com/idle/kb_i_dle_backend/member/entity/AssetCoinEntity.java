package com.idle.kb_i_dle_backend.member.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "coin")
public class AssetCoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int index; // Primary Key with auto-increment

    @Column(name = "uid", nullable = false)
    private int uid; // 사용자 ID

    @Column(name = "currency", length = 10, nullable = false)
    private String currency; // 암호화폐 종류

    @Column(name = "balance", nullable = false)
    private double balance; // 잔액

    @Column(name = "avg_buy_price", nullable = false)
    private double avgBuyPrice; // 평균 구매 가격

    @Column(name = "unit_currency", length = 10, nullable = false)
    private String unitCurrency; // 화폐 단위 (ex: USD, KRW)

    @Column(name = "prod_category", length = 100, nullable = false)
    private String prodCategory; // 상품 카테고리

    @Column(name = "add_date", nullable = false)
    private Timestamp addDate; // 추가된 날짜

    @Column(name = "delete_date")
    private Date deleteDate; // 삭제된 날짜 (nullable)

    // 기본 생성자와 Lombok을 통한 Getter/Setter 자동 생성
}
