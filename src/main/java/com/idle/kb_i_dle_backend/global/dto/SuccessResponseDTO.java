package com.idle.kb_i_dle_backend.global.dto;

import lombok.Getter;

@Getter
public class SuccessResponseDTO {
    private final Boolean success;
    private final DataDTO response;

    public SuccessResponseDTO(Boolean success, Object data) {
        this.success = success;
        this.response = new DataDTO(data);
    }

}
