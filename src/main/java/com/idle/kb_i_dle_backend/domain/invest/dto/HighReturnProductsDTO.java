package com.idle.kb_i_dle_backend.domain.invest.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HighReturnProductsDTO {
    private List<HighReturnProductDTO> products;
}
