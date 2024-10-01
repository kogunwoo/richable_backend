package com.idle.kb_i_dle_backend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
public class ResponseDTO {
    private final Boolean success;
    private final DataDTO response;

    public ResponseDTO(Boolean success, Object data) {
        this.success = success;
        this.response = new DataDTO(data);
    }
}
