package com.idle.kb_i_dle_backend.domain.invest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HighReturnProductDTO {
    private String category;
    private String name;
    private Integer price;
    private String rate;

}
