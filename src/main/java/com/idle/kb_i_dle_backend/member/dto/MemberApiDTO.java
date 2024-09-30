package com.idle.kb_i_dle_backend.member.dto;

public class MemberApiDTO {

    private String bank;
    private ApiDetailDTO stock;
    private ApiDetailDTO coin;

    // Getter 및 Setter
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public ApiDetailDTO getStock() {
        return stock;
    }

    public void setStock(ApiDetailDTO stock) {
        this.stock = stock;
    }

    public ApiDetailDTO getCoin() {
        return coin;
    }

    public void setCoin(ApiDetailDTO coin) {
        this.coin = coin;
    }

    public static class ApiDetailDTO {
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
}
