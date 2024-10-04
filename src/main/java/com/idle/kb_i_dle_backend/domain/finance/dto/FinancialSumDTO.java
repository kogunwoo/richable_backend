package com.idle.kb_i_dle_backend.domain.finance.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinancialSumDTO {
    private BigDecimal amount;  // 금융 자산 합계

    public FinancialSumDTO(BigDecimal amount) {
        this.amount = amount;
    }
}
