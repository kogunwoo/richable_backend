package com.idle.kb_i_dle_backend.domain.finance.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "spot",catalog = "asset") //name =table명 , catalog =  schema
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`index`")
    private Integer index;


    @ManyToOne
    @JoinColumn(name = "uid") // foreign key 이름 설정
    private Member uid;

    @NotNull
    @Column(length = 20)
    private String category;

    @NotNull
    @Column(length = 50)
    private String name;

    @NotNull
    private Long price;

    @Column(length = 100)
    private String prod_category;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "add_date")
    private Date addDate;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "delete_date", nullable = true)
    private Date deleteDate;

    // 엔티티가 처음 영속화될 때(addDate를 자동으로 설정)
    @PrePersist
    protected void onCreate() {
        this.addDate = new Date();  // 현재 시간을 자동으로 설정
    }

}
