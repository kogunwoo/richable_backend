package com.idle.kb_i_dle_backend.domain.invest.dto;

import com.idle.kb_i_dle_backend.domain.finance.entity.Bank;
import com.idle.kb_i_dle_backend.domain.finance.entity.Bond;
import com.idle.kb_i_dle_backend.domain.finance.entity.Coin;
import com.idle.kb_i_dle_backend.domain.finance.entity.Stock;

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

    public InvestDTO(Bank userBank){
        this.assetId = userBank.getIndex();
        this.category = userBank.getCategory();
        this.name = userBank.getName();
        this.price = userBank.getBalanceAmt();
    }
    public InvestDTO(Bond userBond){
        this.assetId = userBond.getIndex();
        this.category = userBond.getCategory();
        this.name = userBond.getName();
        this.price = Long.valueOf(userBond.getPrice());
    }
    public InvestDTO(Coin coin){
        this.assetId = coin.getIndex();
        this.category = coin.getCategory();
        this.name = coin.getCurrency();
        this.price =calculateCoinPrice(coin);
    }

    public InvestDTO(Stock stock){
        this.assetId = stock.getIndex();
        this.category = stock.getCategory();
        this.name = stock.getPrdtName();
        this.price = calculateStockPrice(stock);
    }

    private Long calculateStockPrice(Stock stock) {
        return Long.valueOf(stock.getHldgQty() * stock.getAvgBuyPrice());
    }
    private Long calculateCoinPrice(Coin coin) {
        return (long) (coin.getBalance() * coin.getAvgBuyPrice());
    }
    private Long calculateBondPrice(Bond bond) {
        return (long) (bond.getPrice() * bond.getCnt());
    }

    public static List<InvestDTO> fromUserBankList(List<Bank> banks) {
        return banks.stream()
                .map(InvestDTO::new)
                .collect(Collectors.toList());
    }

    public static List<InvestDTO> fromBondList(List<Bond> bonds) {
        return bonds.stream()
                .map(InvestDTO::new)
                .collect(Collectors.toList());
    }
    public static List<InvestDTO> fromCoinList(List<Coin> coins) {
        return coins.stream()
                .map(InvestDTO::new)
                .collect(Collectors.toList());
    }
    public static List<InvestDTO> fromStockList(List<Stock> stocks) {
        return stocks.stream()
                .map(InvestDTO::new)
                .collect(Collectors.toList());
    }

}
