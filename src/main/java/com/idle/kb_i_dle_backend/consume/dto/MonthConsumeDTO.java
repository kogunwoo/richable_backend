package com.idle.kb_i_dle_backend.consume.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MonthConsumeDTO {
    private int cntMonth;
    private int cntYear;
    private String cntDate;
    List<Long> prices;
}
