package com.idle.kb_i_dle_backend.domain.member.dto;

public class CoinData {
    private String base;  // 기본 정보
    private String secret;  // 비밀 키
    private String app;  // 앱 정보

    // Getters and Setters
    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
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
