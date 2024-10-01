package com.idle.kb_i_dle_backend.domain.invest.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class RecommendedProductDTO {
    private String category;
    private String name;
    private Integer price;
}
