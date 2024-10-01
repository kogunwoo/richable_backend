package com.idle.kb_i_dle_backend.domain.outcome.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MonthOutcomeDTO {
    private int cntMonth;
    private int cntYear;
    List<Long> prices;
}
