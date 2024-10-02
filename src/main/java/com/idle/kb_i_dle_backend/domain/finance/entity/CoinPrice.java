package com.idle.kb_i_dle_backend.domain.finance.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
@Table(name = "coin_price", catalog = "product")
@Getter
@Setter
public class CoinPrice implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer index;

    @Column(name = "coin_name", length = 20, nullable = false)
    private String coin_name;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "price", precision = 20, scale = 8)
    private BigDecimal price;
}
