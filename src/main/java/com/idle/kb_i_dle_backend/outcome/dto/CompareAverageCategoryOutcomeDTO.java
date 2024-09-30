package com.idle.kb_i_dle_backend.outcome.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompareAverageCategoryOutcomeDTO {
    private String category;
    private long mySum;
    private long averageSum;
}
