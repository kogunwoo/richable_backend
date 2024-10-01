package com.idle.kb_i_dle_backend.domain.invest.dto;

import com.idle.kb_i_dle_backend.domain.finance.entity.UserBank;
import com.idle.kb_i_dle_backend.domain.income.dto.IncomeDTO;
import com.idle.kb_i_dle_backend.domain.income.entity.Income;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvestDTO {
    private Integer assetId;
    private String category;
    private String name;
    private Long price;

    public InvestDTO(UserBank userBank){
        this.assetId = userBank.getIndex();
        this.category = userBank.getCategory();
        this.name = userBank.getName();
        this.price = userBank.getBalanceAmt();
    }
    // UserBank 리스트를 InvestDTO 리스트로 변환하는 정적 메서드 추가
    public static List<InvestDTO> fromUserBankList(List<UserBank> userBanks) {
        return userBanks.stream()
                .map(InvestDTO::new)
                .collect(Collectors.toList());
    }

}
