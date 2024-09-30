package com.idle.kb_i_dle_backend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {
    private final Boolean success;
    private final DataDTO response;
}
