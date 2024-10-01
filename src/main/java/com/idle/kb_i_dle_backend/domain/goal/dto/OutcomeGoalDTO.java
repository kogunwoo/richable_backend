package com.idle.kb_i_dle_backend.domain.goal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OutcomeGoalDTO {
    private Integer index;
    private Long gather;
    private String title;
    private Long amount;
    private Date date;
    private Integer priority;
}
