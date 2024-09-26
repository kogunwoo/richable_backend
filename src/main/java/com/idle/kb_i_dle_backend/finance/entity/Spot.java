package com.idle.kb_i_dle_backend.finance.entity;

import com.idle.kb_i_dle_backend.member.entity.User;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Table(name = "spot",catalog = "asset") //name =table명 , catalog =  schema
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "uid") // foreign key 이름 설정
    private User uid;

    private String category;

    private String name;

    private Long price;

    private String prod_category;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "add_date")
    private Date addDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "delete_date")
    private Date deleteDate;

}
