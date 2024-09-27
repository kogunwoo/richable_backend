package com.idle.kb_i_dle_backend.consume.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompareWithAvgDTO {
    private String category;
    private Long sum;
}
