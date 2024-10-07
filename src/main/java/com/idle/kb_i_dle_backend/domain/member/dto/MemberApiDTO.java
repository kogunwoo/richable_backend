
package com.idle.kb_i_dle_backend.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberApiDTO {
    private Integer uid;  // uid 필드 추가
    private String apiBank;
    private String apiStock;
    private String apiStockToken;
    private String apiStockSecret;
    private String apiStockApp;
    private String apiCoin;
    private String apiCoinApp;
    private String apiCoinSecret;
}

