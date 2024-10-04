package com.idle.kb_i_dle_backend.domain.finance.entity;

import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock", catalog = "asset")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`index`")
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member uid;

    @NotNull
    private Integer pdno;

    @NotNull
    @Column(name = "prdt_name")
    private String prdtName;

    @Column(name = "hldg_qty")
    private Integer hldgQty;

    @Column(name = "prod_category")
    private String category;

    @Column(name = "avg_buy_price")
    private Integer avgBuyPrice;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "add_date")
    private Date addDate;

    @Column(name = "delete_date")
    private Date deleteDate;

    // 엔티티가 처음 영속화될 때(addDate를 자동으로 설정)
    @PrePersist
    protected void onCreate() {
        this.addDate = new Date();  // 현재 시간을 자동으로 설정
    }
}
