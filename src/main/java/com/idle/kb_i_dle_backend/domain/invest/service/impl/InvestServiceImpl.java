package com.idle.kb_i_dle_backend.domain.invest.service.impl;

import com.idle.kb_i_dle_backend.domain.finance.entity.*;
import com.idle.kb_i_dle_backend.domain.finance.repository.BankRepository;
import com.idle.kb_i_dle_backend.domain.finance.repository.BondRepository;
import com.idle.kb_i_dle_backend.domain.finance.repository.CoinRepository;
import com.idle.kb_i_dle_backend.domain.finance.repository.StockRepository;
import com.idle.kb_i_dle_backend.domain.invest.dto.*;
import com.idle.kb_i_dle_backend.domain.invest.service.InvestService;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import com.idle.kb_i_dle_backend.domain.member.repository.UserRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class InvestServiceImpl implements InvestService {
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final BondRepository bondRepository;
    private final CoinRepository coinRepository;
    private final StockRepository stockRepository;

    public InvestServiceImpl(BankRepository bankRepository, UserRepository userRepository,
                             BondRepository bondRepository,CoinRepository coinRepository,StockRepository stockRepository) {
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
        this.bondRepository = bondRepository;
        this.coinRepository = coinRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public List<InvestDTO> getInvestList() throws Exception {
        Member tempUser = userRepository.findByUid(1).orElseThrow();
        List<UserBank> userBanks = bankRepository.findByUid(tempUser);
        List<UserBond> userBonds = bondRepository.findByUid(tempUser);
        List<UserCoin> userCoins = coinRepository.findByUid(tempUser);
        List<UserStock> userStocks = stockRepository.findByUid(tempUser);

        List<InvestDTO> investDTOs = new ArrayList<>();
        investDTOs.addAll(InvestDTO.fromUserBankList(userBanks));
        investDTOs.addAll(InvestDTO.fromBondList(userBonds));
        investDTOs.addAll(InvestDTO.fromCoinList(userCoins));
        investDTOs.addAll(InvestDTO.fromStockList(userStocks));

        return investDTOs;
    }

    @Override
    public long totalAsset() throws Exception {
        List<InvestDTO> investDTOs = getInvestList();

        return investDTOs.stream()
                .mapToLong(InvestDTO::getPrice)
                .sum();
    }

    @Override
    public MaxPercentageCategoryDTO getMaxPercentageCategory() throws Exception {
        List<CategorySumDTO> categorySums = getInvestmentTendency();

        return categorySums.stream()
                .max(Comparator.comparingDouble(CategorySumDTO::getPercentage))
                .map(category -> new MaxPercentageCategoryDTO(
                        category.getCategory(),
                        category.getTotalPrice(),
                        category.getPercentage()))
                .orElseThrow(() -> new Exception("No categories found"));
    }

    @Override
    public AvailableCashDTO getAvailableCash() throws Exception {
        Member tempUser = userRepository.findByUid(1).orElseThrow(() -> new Exception("User not found"));
        List<UserBank> userBanks = bankRepository.findByUidAndSpecificCategoriesAndDeleteDateIsNull(tempUser);

        Long totalAvailableCash = userBanks.stream()
                .mapToLong(UserBank::getBalanceAmt)
                .sum();

        Long totalAsset = totalAsset();

        return new AvailableCashDTO(totalAvailableCash, totalAsset);
    }

    @Override
    public List<CategorySumDTO> getInvestmentTendency() throws Exception {
        Member tempUser = userRepository.findByUid(1).orElseThrow(() -> new Exception("User not found"));
        List<InvestDTO> investDTOs = getInvestList();

        Map<String, Long> categorySums = investDTOs.stream()
                .collect(Collectors.groupingBy(InvestDTO::getCategory,
                        Collectors.summingLong(InvestDTO::getPrice)));

        long totalInvestment = totalAsset();

        return categorySums.entrySet().stream()
                .map(entry -> {
                    String category = entry.getKey();
                    Long totalPrice = entry.getValue();
                    Double percentage = (totalPrice.doubleValue() / totalInvestment) * 100;
                    return new CategorySumDTO(category, totalPrice, percentage);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<RecommendedProductDTO> getRecommendedProducts() throws Exception {
        MaxPercentageCategoryDTO maxCategory = getMaxPercentageCategory();
        List<RecommendedProductDTO> recommendedProducts = new ArrayList<>();

        if ("공격형".equals(maxCategory.getTendency())) {
            List<CoinList> coins = coinRepository.findTop5ByOrderByPriceDesc();
            List<StockList> stocks = stockRepository.findTop5ByOrderByAvgBuyPriceDesc();

            recommendedProducts.addAll(coins.stream()
                    .map(coin -> new RecommendedProductDTO("coin", coin.getCoinName(), Integer.parseInt(String.valueOf(coin.getClosingPrice())) ))
                    .collect(Collectors.toList()));

            recommendedProducts.addAll(stocks.stream()
                    .map(stock -> new RecommendedProductDTO("stock", stock.getKrStockNm(), stock.getPrice()))
                    .collect(Collectors.toList()));
        }

        return recommendedProducts;
    }


}