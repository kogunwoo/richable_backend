package com.idle.kb_i_dle_backend.member.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "income")
public class AssetIncomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int index; // Primary Key

    @Column(name = "uid", nullable = false)
    private int uid; // 사용자 ID

    @Column(name = "type", length = 100, nullable = false)
    private String type; // 수입의 종류 (예: 월급, 보너스)

    @Column(name = "amount", nullable = false)
    private long amount; // 수입 금액

    @Column(name = "date", nullable = false)
    private Date date; // 수입 발생 날짜

    @Column(name = "descript", length = 255)
    private String descript; // 수입에 대한 설명

    @Column(name = "memo", length = 255)
    private String memo; // 메모

    // 기본 생성자와 Lombok을 통한 Getter/Setter 자동 생성
}
