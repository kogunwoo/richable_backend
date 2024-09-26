package com.idle.kb_i_dle_backend.member.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
//@Entity
//@Table(name = "stock")
public class AssetStockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int index; // Primary Key

    @Column(name = "uid", nullable = false)
    private int uid; // 사용자 ID

    @Column(name = "pdno", nullable = false)
    private int pdno; // 제품 번호

    @Column(name = "prdt_name", length = 100, nullable = false)
    private String prdtName; // 제품 이름

    @Column(name = "hldg_qty", nullable = false)
    private int hldgQty; // 보유 수량

    @Column(name = "prod_category", length = 100, nullable = false)
    private String prodCategory; // 상품 카테고리

    @Column(name = "add_date", nullable = false)
    private Timestamp addDate; // 추가된 날짜

    @Column(name = "delete_date")
    private Date deleteDate; // 삭제된 날짜 (nullable)

    // 기본 생성자와 Lombok을 통한 Getter/Setter 자동 생성
}
