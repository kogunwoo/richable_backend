package com.idle.kb_i_dle_backend.domain.goal.entity;

import com.idle.kb_i_dle_backend.domain.member.entity.User;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User uid;

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
}
