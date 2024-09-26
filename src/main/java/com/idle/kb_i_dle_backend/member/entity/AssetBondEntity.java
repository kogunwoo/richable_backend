package com.idle.kb_i_dle_backend.member.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
//@Entity
//@Table(name = "bond")
public class AssetBondEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int index; // Primary Key

    @Column(name = "uid", nullable = false)
    private int uid;

    @Column(name = "itms_nm", length = 255, nullable = false)
    private String itmsNm; // 종목명

    @Column(name = "cnt", nullable = false)
    private int cnt; // 수량

    @Column(name = "prod_category", length = 100, nullable = false)
    private String prodCategory; // 상품 카테고리

    @Column(name = "per_price", nullable = false)
    private int perPrice; // 단가

    @Column(name = "add_date", nullable = false)
    private Timestamp addDate; // 추가된 날짜

    @Column(name = "delete_date")
    private Date deleteDate; // 삭제된 날짜 (nullable)

    // 기본 생성자와 Lombok을 통한 Getter/Setter 자동 생성
}
