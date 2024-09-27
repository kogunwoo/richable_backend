package com.idle.kb_i_dle_backend.member.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
//@Entity
//@Table(name = "spot")
public class AssetSpotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int index; // Primary Key

    @Column(name = "uid", nullable = false)
    private int uid; // 사용자 ID

    @Column(name = "category", length = 20, nullable = false)
    private String category; // 카테고리 (예: 특정 상품/서비스의 종류)

    @Column(name = "name", length = 50, nullable = false)
    private String name; // 이름 (예: 특정 장소나 상품의 이름)

    @Column(name = "price", nullable = false)
    private long price; // 가격

    @Column(name = "prod_category", length = 100, nullable = false)
    private String prodCategory; // 상품 카테고리

    @Column(name = "add_date", nullable = false)
    private Timestamp addDate; // 추가된 날짜

    @Column(name = "delete_date")
    private Date deleteDate; // 삭제된 날짜 (nullable)

    // 기본 생성자와 Lombok을 통한 Getter/Setter 자동 생성
}
