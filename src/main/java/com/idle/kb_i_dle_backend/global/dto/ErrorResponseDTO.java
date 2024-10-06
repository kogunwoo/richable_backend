package com.idle.kb_i_dle_backend.global.dto;

import com.idle.kb_i_dle_backend.global.response.ErrorResponse;
import lombok.Data;

@Data
public class ErrorResponseDTO {
    private final Boolean success;
    private final Object error;

    public ErrorResponseDTO(Object errorResponse) {
        this.success = false;
        this.error = errorResponse;
    }
}
