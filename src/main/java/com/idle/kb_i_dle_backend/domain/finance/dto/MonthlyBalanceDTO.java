package com.idle.kb_i_dle_backend.domain.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class MonthlyBalanceDTO {
    private String month;
    private long totalIncome;
    private long totalOutcome;
    private long balance;
}
