package com.idle.kb_i_dle_backend.domain.finance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinancialSumDTO {
    private long amount;  // 금융 자산 합계

    public FinancialSumDTO(long amount) {
        this.amount = amount;
    }
}
