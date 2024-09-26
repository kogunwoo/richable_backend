package com.idle.kb_i_dle_backend.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SuccessResponseDTO {

    private String success;
    private Map<String, Object> response;

    public SuccessResponseDTO(String success, Map<String, Object> response) {
        this.success = success;
        this.response = response;
    }


}