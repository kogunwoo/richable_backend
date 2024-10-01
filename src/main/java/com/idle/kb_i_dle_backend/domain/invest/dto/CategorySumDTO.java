package com.idle.kb_i_dle_backend.domain.invest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategorySumDTO {
    private String category;
    private Long totalPrice;
    private Double percentage;

    public CategorySumDTO(String category, Long totalPrice) {
        this.category = category;
        this.totalPrice = totalPrice;
    }
}