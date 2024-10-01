package com.idle.kb_i_dle_backend.domain.invest.dto;

import com.idle.kb_i_dle_backend.domain.finance.entity.Spot;
import com.idle.kb_i_dle_backend.domain.finance.entity.UserBank;
import com.idle.kb_i_dle_backend.domain.finance.entity.UserBond;
import com.idle.kb_i_dle_backend.domain.finance.entity.UserCoin;
import com.idle.kb_i_dle_backend.domain.finance.entity.UserStock;
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
    public InvestDTO(UserBond userBond){
        this.assetId = userBond.getIndex();
        this.category = userBond.getCategory();
        this.name = userBond.getName();
        this.price = Long.valueOf(userBond.getPrice());
    }
    public InvestDTO(UserCoin userCoin){
        this.assetId = userCoin.getIndex();
        this.category = userCoin.getCategory();
        this.name = userCoin.getCurrency();
        this.price =calculateCoinPrice(userCoin);
    }

    public InvestDTO(UserStock userStock){
        this.assetId = userStock.getIndex();
        this.category = userStock.getCategory();
        this.name = userStock.getPrdtName();
        this.price = calculateStockPrice(userStock);
    }

    private Long calculateStockPrice(UserStock userStock) {
        return Long.valueOf(userStock.getHldgQty() * userStock.getAvgBuyPrice());
    }
    private Long calculateCoinPrice(UserCoin userCoin) {
        return (long) (userCoin.getBalance() * userCoin.getAvgBuyPrice());
    }
    private Long calculateBondPrice(UserBond userBond) {
        return (long) (userBond.getPrice() * userBond.getCnt());
    }

    public static List<InvestDTO> fromUserBankList(List<UserBank> userBanks) {
        return userBanks.stream()
                .map(InvestDTO::new)
                .collect(Collectors.toList());
    }

    public static List<InvestDTO> fromBondList(List<UserBond> userBond) {
        return userBond.stream()
                .map(InvestDTO::new)
                .collect(Collectors.toList());
    }
    public static List<InvestDTO> fromCoinList(List<UserCoin> userCoin) {
        return userCoin.stream()
                .map(InvestDTO::new)
                .collect(Collectors.toList());
    }
    public static List<InvestDTO> fromStockList(List<UserStock> userStock) {
        return userStock.stream()
                .map(InvestDTO::new)
                .collect(Collectors.toList());
    }

}
