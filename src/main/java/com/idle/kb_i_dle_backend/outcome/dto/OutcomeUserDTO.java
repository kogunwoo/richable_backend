package com.idle.kb_i_dle_backend.outcome.dto;

import lombok.Getter;

import java.util.Date;

@Getter

public class OutcomeUserDTO {
    private int index;
    private int uid;
    private String expCategory;
    private Date date;
    private Long amount;
    private String descript;
    private String memo;

    public OutcomeUserDTO(int index, int uid, String expCategory, Date date, Long amount, String descript, String memo) {
        this.index = index;
        this.uid = uid;
        this.expCategory = expCategory;
        this.date = date;
        this.amount = amount;
        this.descript = descript;
        this.memo = memo;

    }

    public OutcomeUserDTO() {}

}
