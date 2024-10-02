package com.idle.kb_i_dle_backend.domain.goal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AssetGoalDTO {
    private int index;
    private long gather;
    private String title;
    private long amount;
    private Date date;
    private int remaindate;


    public AssetGoalDTO(int index, String title, long amount, Date date) {
        this.index = index;
        this.gather = 0;
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.remaindate = 0;
    }

    public AssetGoalDTO(int index,long gather ,String title, long amount, Date date, int remaindate) {
        this.index = index;
        this.gather = gather;
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.remaindate = remaindate;
    }
}
