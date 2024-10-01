package com.idle.kb_i_dle_backend.domain.outcome.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Simulation6MonthDTO {
    private List<String> months;
    private List<Long> saveAmount;
    private List<Long> possibleSaveAmount;
}
