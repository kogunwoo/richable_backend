package com.idle.kb_i_dle_backend.domain.finance.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "stock_price", catalog = "product")
public class StockPrice implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer index;

    @Column(name = "standard_code", length = 20, nullable = false)
    private String standard_code;

    @Column(name = "stock_nm", length = 20)
    private String stock_nm;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "price")
    private Integer price;

}
