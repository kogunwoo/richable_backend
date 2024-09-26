package com.idle.kb_i_dle_backend.member.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiDetailDTO {
    private String base;
    private String token;
    private String secret;
    private String app;

    // Getter 및 Setter
    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
