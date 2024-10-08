package com.idle.kb_i_dle_backend.domain.outcome.dto;

import lombok.Getter;

import java.math.BigDecimal;
@Getter

public class OutcomeAverageDTO {
    private int index;
    private String ageGroup;
    private String outcomeExp;
    private int outcome;
    private BigDecimal householdSize;
    private String quater;

    // 인자를 받는 생성자를 추가합니다.
    public OutcomeAverageDTO(int index, String ageGroup, String outcomeExp, int outcome, BigDecimal householdSize, String quater) {
        this.index = index;
        this.ageGroup = ageGroup;
        this.outcomeExp = outcomeExp;
        this.outcome = outcome;
        this.householdSize = householdSize;
        this.quater = quater;
    }

    // 기본 생성자 (필요한 경우)
    public OutcomeAverageDTO() {}

    // Getter와 Setter 추가 (이미 있을 수 있음)
}
