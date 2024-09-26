package com.idle.kb_i_dle_backend.member.dto;

public class ResponseDataDTO {
    private boolean certification;
    private ApiDataDTO api;

    // Getter 및 Setter
    public boolean isCertification() {
        return certification;
    }

    public void setCertification(boolean certification) {
        this.certification = certification;
    }

    public ApiDataDTO getApi() {
        return api;
    }

    public void setApi(ApiDataDTO api) {
        this.api = api;
    }
}
