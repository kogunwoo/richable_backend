package com.idle.kb_i_dle_backend.domain.finance.entity;

import com.idle.kb_i_dle_backend.domain.member.entity.User;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Table(name = "bond", catalog = "asset")
@Getter
public class UserBond {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User uid;

    @NotNull
    @Column(name = "itms_nm")
    private String  name;

    @NotNull
    private Integer cnt;

    @Column(name = "prod_category", length = 100)
    private String category;

    @Column(name = "per_price")
    private Integer price;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "add_date")
    private Date addDate;

    @Column(name = "delete_date")
    private Date deleteDate;

}
