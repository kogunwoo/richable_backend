package com.idle.kb_i_dle_backend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {
    private final Boolean success;
    private final String message;
}
