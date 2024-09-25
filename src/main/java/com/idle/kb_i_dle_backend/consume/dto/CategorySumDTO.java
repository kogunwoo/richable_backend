package com.idle.kb_i_dle_backend.consume.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorySumDTO {

    //long -값타입 Long-WrapperClass
    private Long categorySum;
    private String category;


    public CategorySumDTO( String category, long categorySum) {
        this.categorySum = categorySum;
        this.category = category;
    }

    public CategorySumDTO() {}
}
