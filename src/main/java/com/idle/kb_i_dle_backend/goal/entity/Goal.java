package com.idle.kb_i_dle_backend.goal.entity;

import com.idle.kb_i_dle_backend.member.entity.User;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "user_goal", catalog = "user_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User uid;

    private String category;

    private String title;

    private Long amount;

    @Column(name = "set_date")
    private Date date;

    private Integer priority;

    @ColumnDefault("false")
    @Column(name = "is_achive")
    private Boolean isAchive;
}
