package com.idle.kb_i_dle_backend.domain.goal.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutcomeGoalDTO {
    private int index;
    private long gather;
    private String title;
    private long amount;
    private Date date;
    private int priority;


    public OutcomeGoalDTO(int index, long gather, String title, long amount, Date date, int priority) {
        this.index = index;
        this.gather = gather;
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.priority = priority;
    }


}
