package com.idle.kb_i_dle_backend.domain.member.dto;

public class TokenResponseDTO {
    private String success;
    private ResponseDataDTO response;

    // Getter 및 Setter
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ResponseDataDTO getResponse() {
        return response;
    }

    public void setResponse(ResponseDataDTO response) {
        this.response = response;
    }
}
