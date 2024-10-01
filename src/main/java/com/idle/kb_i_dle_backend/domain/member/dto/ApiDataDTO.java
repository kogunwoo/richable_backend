package com.idle.kb_i_dle_backend.domain.member.dto;

public class ApiDataDTO {
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
}
