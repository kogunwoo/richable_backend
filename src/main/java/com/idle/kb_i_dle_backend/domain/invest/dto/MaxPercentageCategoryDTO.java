package com.idle.kb_i_dle_backend.domain.invest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaxPercentageCategoryDTO {
    private String category;
    private Long totalPrice;
    private Double percentage;
    private String tendency;

    public MaxPercentageCategoryDTO(String category, Long totalPrice, Double percentage) {
        this.category = category;
        this.totalPrice = totalPrice;
        this.percentage = percentage;
        this.tendency = determineTendency(category);
    }

    private String determineTendency(String category) {
        return category.equalsIgnoreCase("coin") || category.equalsIgnoreCase("stock")
                ? "공격형" : "안정형";
    }
}
