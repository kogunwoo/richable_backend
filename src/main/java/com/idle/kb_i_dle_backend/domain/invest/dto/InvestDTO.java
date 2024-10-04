package com.idle.kb_i_dle_backend.domain.invest.dto;

import com.idle.kb_i_dle_backend.domain.finance.entity.Bank;
import com.idle.kb_i_dle_backend.domain.finance.entity.Bond;
import com.idle.kb_i_dle_backend.domain.finance.entity.Coin;
import com.idle.kb_i_dle_backend.domain.finance.entity.Stock;
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

    public InvestDTO(Bank bank){
        this.assetId = bank.getIndex();
        this.category = bank.getCategory();
        this.name = bank.getName();
        this.price = bank.getBalanceAmt();
    }
    public InvestDTO(Bond bond){
        this.assetId = bond.getIndex();
        this.category = bond.getCategory();
        this.name = bond.getName();
        this.price = Long.valueOf(bond.getPrice());
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

    public static List<InvestDTO> fromBondList(List<Bond> bond) {
        return bond.stream()
                .map(InvestDTO::new)
                .collect(Collectors.toList());
    }
    public static List<InvestDTO> fromCoinList(List<Coin> coin) {
        return coin.stream()
                .map(InvestDTO::new)
                .collect(Collectors.toList());
    }
    public static List<InvestDTO> fromStockList(List<Stock> stock) {
        return stock.stream()
                .map(InvestDTO::new)
                .collect(Collectors.toList());
    }

}
