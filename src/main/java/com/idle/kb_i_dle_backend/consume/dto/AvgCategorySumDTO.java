package com.idle.kb_i_dle_backend.consume.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvgCategorySumDTO {
    private String category;
    private long categorySum;


    public AvgCategorySumDTO( String category, long categorySum) {
        this.categorySum = categorySum;
        this.category = category;
    }

    public AvgCategorySumDTO() {}
}
