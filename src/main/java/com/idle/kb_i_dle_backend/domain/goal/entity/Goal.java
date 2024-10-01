package com.idle.kb_i_dle_backend.domain.goal.entity;

import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "user_goal", catalog = "user_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Goal {

    private static final int ACHIVE_PRIORIY = 999;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_b")
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member uid;

    private String category;

    private String title;

    private Long amount;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "set_date")
    private Date date;

    private Integer priority;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_achive")
    private Boolean isAchive;

    // 엔티티가 처음 영속화될 때(Date를 자동으로 설정)
    @PrePersist
    protected void onCreate() {
        this.date = new Date();  // 현재 시간을 자동으로 설정
    }


    public Goal(Member uid,String category, String title, Long amount, Integer priority){
        this.uid = uid;
        this.category = category;
        this.title = title;
        this.amount = amount;
        this.priority = priority;
        this.isAchive = false;

    }

    public void updateToAchive(){
        this.isAchive = true;
        this.priority = ACHIVE_PRIORIY;
    }



}
