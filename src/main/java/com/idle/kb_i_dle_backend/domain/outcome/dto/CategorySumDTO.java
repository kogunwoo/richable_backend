package com.idle.kb_i_dle_backend.domain.outcome.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategorySumDTO {
    //long -값타입 Long-WrapperClass
    private String category;
    private Long sum;
}
