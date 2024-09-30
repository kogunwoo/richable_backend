package com.idle.kb_i_dle_backend.consume.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumeDTO {
    private int wasted;    // 낭비된 금액
    private int monthSum;  // 월 소비 총합
    private int dailyCons; // 일일 소비 금액
    private int consEval;  // 소비 평가 점수
}
