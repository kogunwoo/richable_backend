package com.idle.kb_i_dle_backend.domain.finance.service;

import com.idle.kb_i_dle_backend.domain.finance.dto.*;
import com.idle.kb_i_dle_backend.domain.finance.entity.*;
import com.idle.kb_i_dle_backend.domain.income.repository.IncomeRepository;
import com.idle.kb_i_dle_backend.domain.outcome.repository.OutcomeUserRepository;
import com.idle.kb_i_dle_backend.domain.member.repository.UserRepository;
import com.idle.kb_i_dle_backend.domain.finance.repository.*;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FinanceServiceImpl implements FinanceService {

    private final BankRepository bankRepository;
    private final BondRepository bondRepository;
    private final CoinRepository coinRepository;
    private final SpotRepository spotRepository;
    private final StockRepository stockRepository;
    private final IncomeRepository incomeRepository;
    private final OutcomeUserRepository outComeUserRepository;
    private final UserRepository userRepository;
    private final EntityManagerFactory entityManagerFactory;

    // 소수점 이하 한 자리로 포맷팅할 수 있는 DecimalFormat
    private static final DecimalFormat df = new DecimalFormat("#.#");

    // 금융 자산 합계 계산
    @Override
    public FinancialSumDTO getFinancialAssetsSum(int uid) {
        Optional<Member> member = userRepository.findByUid(uid);
        long financialAssetsSum = calculateFinancialAssetsSum(member);
        return new FinancialSumDTO(financialAssetsSum);
    }

    // 총 자산 합계 계산 (금융 자산 + Spot 자산)
    @Override
    public FinancialSumDTO getTotalAssetsSum(int uid) {
        Optional<Member> member = userRepository.findByUid(uid);
        long totalAssetsSum = calculateFinancialAssetsSum(member) + calculateSpotAssetsSum(member);
        return new FinancialSumDTO(totalAssetsSum);
    }

    // 금융 자산 목록 조회
    @Override
    public List<AssetDTO> getFinancialAsset(int uid) {
        Optional<Member> member = userRepository.findByUid(uid);
        List<AssetDTO> assetList = new ArrayList<>();

            assetList.add(new AssetDTO("예적금", sumBankAssets(member)));
            assetList.add(new AssetDTO("주식", sumStockAssets(member)));
            assetList.add(new AssetDTO("코인", sumCoinAssets(member)));
            //채권은 매입가격으로 계산 (종가가 없음)
            assetList.add(new AssetDTO("채권", sumBondAssets(member)));

        return assetList;
    }

    // 6개월 금융 자산 변동 계산
    @Override
    public List<FinancialChangeDTO> getSixMonthFinancialChanges(int uid) {
        Optional<Member> member = userRepository.findByUid(uid);
        List<FinancialChangeDTO> financialChanges = new ArrayList<>();
        member.ifPresent(m -> {
            for (int i = 0; i < 6; i++) {
                long monthlySum = (i == 0) ? calculateFinancialAssetsSum(Optional.of(m)) : calculateMonthlyAssetsSum(m, i);
                financialChanges.add(new FinancialChangeDTO(i, monthlySum));
            }
        });
        return financialChanges;
    }

    // 6개월 총 자산 변동 계산 (금융 자산 + Spot 자산)
    @Override
    public List<TotalChangeDTO> getSixMonthTotalChanges(int uid) {
        Optional<Member> member = userRepository.findByUid(uid);
        List<TotalChangeDTO> totalChanges = new ArrayList<>();
        member.ifPresent(m -> {
            for (int i = 0; i < 6; i++) {
                long monthlySum = (i == 0) ? (calculateFinancialAssetsSum(Optional.of(m)) + calculateSpotAssetsSum(Optional.of(m)))
                        : calculateMonthlyAssetsSum(m, i) + calculateSpotAssetsSumBefore(m, i);
                totalChanges.add(new TotalChangeDTO(i, monthlySum));
            }
        });
        return totalChanges;
    }

    //6개월 간 저축률
    @Override
    public List<MonthlySavingRateDTO> getMonthlySavingRateTrend(int uid) {
        Optional<Member> member = userRepository.findByUid(uid);
        List<MonthlySavingRateDTO> monthlySavingRates = new ArrayList<>();

//        for (int i = 0; i < 6; i++) {
//            // 해당 월의 시작일과 마지막일 계산
//            LocalDate startOfMonth = LocalDate.now().minusMonths(i).withDayOfMonth(1);
//            LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
//
//            Date startDate = Date.from(startOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
//            Date endDate = Date.from(endOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
//
//            // 소득과 소비 데이터를 가져옴
//            List<Income> incomes = incomeRepository.findByUidAndDateBetween(uid, startDate, endDate);
//            List<OutcomeUser> outComeUsers = outComeUserRepository.findByUidAndDateBetween(uid, startDate, endDate);
//
//            // 총 소득 계산
//            long totalIncome = incomes.stream().mapToLong(Income::getAmount).sum();
//
//            // 총 소비량 계산
//            long totalConsumption = outComeUsers.stream().mapToLong(OutcomeUser::getAmount).sum();
//
//            // 저축 가능 금액 계산 (소득 - 소비)
//            long savings = totalIncome - totalConsumption;
//
//            // 저축률 계산: 저축 가능 금액 / 총 소득 (소득이 0인 경우 저축률을 0으로 설정)
//            double savingRate = totalIncome > 0 ? (double) savings / totalIncome : 0;
//
//            System.out.println(savings);
//            System.out.println(totalIncome);
//
//            // 월별 저축률 DTO에 추가
//            savingRate = Math.floor(savingRate * 10) / 10.0;
//            monthlySavingRates.add(new MonthlySavingRateDTO(i,savingRate));
//        }

        return monthlySavingRates;
    }

    @Override
    public List<StockReturnDTO> getStockReturnTrend(int uid) {
//        Optional<Member> member = userRepository.findByUid(uid);
        return userRepository.findByUid(uid)
                .map(member -> {
        List<StockReturnDTO> stockReturns = new ArrayList<>();
        List<UserStock> userStocks = stockRepository.findAllByUidAndDeleteDateIsNull(member);

        for (int i = 0; i < 6; i++) {
            double totalStockReturn = 0;
            int stockCount = 0;

            for (UserStock stock : userStocks) {
                Date purchaseDateAsDate = stock.getAddDate();
                LocalDate purchaseDate = purchaseDateAsDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentMonthDate = LocalDate.now().minusMonths(i);
                Date endDate = Date.from(currentMonthDate.withDayOfMonth(currentMonthDate.lengthOfMonth())
                        .atStartOfDay(ZoneId.systemDefault()).toInstant());

                if (i == 0) {
                    // 현재 가격과 매입 가격 비교하여 수익률 계산
                    double currentPrice = 50000; // 임의로 설정된 현재 가격
                    double purchasePrice = stockRepository.getStockPriceByPdno(stock.getPdno());

                    if (purchasePrice > 0) {
                        double stockReturn = ((currentPrice / purchasePrice) * 100) - 100;
                        totalStockReturn += stockReturn;
                        stockCount++;
                    }
                } else {
                    // 특정 달의 가격으로 수익률 계산
                    if (!purchaseDate.isAfter(currentMonthDate)) {
                        Double currentMonthPrice = stockRepository.getStockPriceForMonth(stock.getPdno(), i);
                        double purchasePrice = stockRepository.getStockPriceByPdno(stock.getPdno());

                        if (currentMonthPrice != null && purchasePrice > 0) {
                            double stockReturn = ((currentMonthPrice / purchasePrice) * 100) - 100;
                            totalStockReturn += stockReturn;
                            stockCount++;
                        }
                    }
                }
            }

            if (stockCount > 0) {
                double averageStockReturn = totalStockReturn / stockCount;
                stockReturns.add(new StockReturnDTO(i, Double.parseDouble(df.format(averageStockReturn))));
            } else {
                stockReturns.add(new StockReturnDTO(i, 0));
            }
        }

        return stockReturns;
                })
                .orElse(Collections.emptyList());
    }


    //6개월 코인 수익률
    @Override
    public List<CoinReturnDTO> getCoinReturnTrend(int uid) {
        List<CoinReturnDTO> coinReturns = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.##");
        Optional<Member> member =  userRepository.findByUid(uid);

        for (int i = 0; i < 6; i++) {
            double totalCoinReturn = 0;
            int coinCount = 0;

            List<UserCoin> userCoins = coinRepository.findByUid(member);

            for (UserCoin coin : userCoins) {
                Date purchaseDateAsDate = coin.getAddDate();
                LocalDate purchaseDate = purchaseDateAsDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentMonthDate = LocalDate.now().minusMonths(i);
                Date endDate = Date.from(currentMonthDate.withDayOfMonth(currentMonthDate.lengthOfMonth())
                        .atStartOfDay(ZoneId.systemDefault()).toInstant());

                if (i == 0) {
                    // 현재 가격과 매입 가격 비교하여 수익률 계산
                    double currentPrice = 30000; // 임의로 설정된 현재 가격
                    double purchasePrice = coinRepository.findCoinPriceBy(coin.getCurrency());

                    if (purchasePrice > 0) {
                        double coinReturn = ((currentPrice / purchasePrice) * 100) - 100;
                        totalCoinReturn += coinReturn;
                        coinCount++;
                    }
                } else {
                    // 특정 달의 가격으로 수익률 계산
                    if (!purchaseDate.isAfter(currentMonthDate)) {
                        Double currentMonthPrice = coinRepository.getCoinPriceForMonth(coin.getCurrency(), i);
                        double purchasePrice = coinRepository.findCoinPriceBy(coin.getCurrency());

                        if (currentMonthPrice != null && purchasePrice > 0) {
                            double coinReturn = ((currentMonthPrice / purchasePrice) * 100) - 100;
                            totalCoinReturn += coinReturn;
                            coinCount++;
                        }
                    }
                }
            }

            if (coinCount > 0) {
                double averageCoinReturn = totalCoinReturn / coinCount;
                coinReturns.add(new CoinReturnDTO(i, Double.parseDouble(df.format(averageCoinReturn))));
            } else {
                coinReturns.add(new CoinReturnDTO(i, 0.0));
            }
        }
        return coinReturns;
    }

    //6개월 채권 수익률
    @Override
    public List<BondReturnDTO> getBondReturnTrend(int uid) {
        List<BondReturnDTO> bondReturns = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.##");
        Optional<Member> member =  userRepository.findByUid(uid);

        for (int i = 0; i < 6; i++) {
            double totalBondReturn = 0;
            int bondCount = 0;

            List<UserBond> userBonds = bondRepository.findByUid(member);

            for (UserBond bond : userBonds) {
                Date purchaseDateAsDate = bond.getAddDate();
                LocalDate purchaseDate = purchaseDateAsDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentMonthDate = LocalDate.now().minusMonths(i);
                Date endDate = Date.from(currentMonthDate.withDayOfMonth(currentMonthDate.lengthOfMonth())
                        .atStartOfDay(ZoneId.systemDefault()).toInstant());

                if (i == 0) {
                    // 현재 가격과 매입 가격 비교하여 수익률 계산
                    double currentPrice = 100000; // 임의로 설정된 현재 가격
                    double purchasePrice = bondRepository.
                            getPriceByName(bond.getName());

                    if (purchasePrice > 0) {
                        double bondReturn = ((currentPrice / purchasePrice) * 100) - 100;
                        totalBondReturn += bondReturn;
                        bondCount++;
                    }
                } else {
                    // 특정 달의 가격으로 수익률 계산
                    if (!purchaseDate.isAfter(currentMonthDate)) {
                        Double currentMonthPrice = bondRepository.getBondPriceForMonth(bond.getName(), i);
                        double purchasePrice = bondRepository.getPriceByName(bond.getName());

                        if (currentMonthPrice != null && purchasePrice > 0) {
                            double bondReturn = ((currentMonthPrice / purchasePrice) * 100) - 100;
                            totalBondReturn += bondReturn;
                            bondCount++;
                        }
                    }
                }
            }

            if (bondCount > 0) {
                double averageBondReturn = totalBondReturn / bondCount;
                bondReturns.add(new BondReturnDTO(i, Double.parseDouble(df.format(averageBondReturn))));
            } else {
                bondReturns.add(new BondReturnDTO(i, 0.0));
            }
        }
        return bondReturns;
    }



    // Helper 메서드: 다양한 타입을 double로 변환
    private double convertToDouble(Object obj) {
        if (obj instanceof Integer) {
            return ((Integer) obj).doubleValue();
        } else if (obj instanceof Double) {
            return (Double) obj;
        } else {
            return Double.parseDouble(obj.toString());
        }
    }

    private long calculateFinancialAssetsSum(Optional<Member> uid) {
        return sumBankAssets(uid) + sumBondAssets(uid) + sumStockAssets(uid) + sumCoinAssets(uid);
    }


    // 은행 자산 합계
    private long sumBankAssets(Optional<Member> uid) {
        return bankRepository.findAllByUidAndDeleteDateIsNull(uid).stream().mapToLong(UserBank::getBalanceAmt).sum();
    }

    // 주식 자산 합계
    public long sumStockAssets(Optional<Member> memberOpt) {
        return memberOpt.map(member ->
                stockRepository.getStockBalanceAndPrice(member.getUid()).stream()
                        .mapToLong(row -> (long) (convertToDouble(row[0]) * convertToDouble(row[1])))
                        .sum()
        ).orElse(0L);
    }

    // 코인 자산 합계
    private long sumCoinAssets(Optional<Member> uid) {
        return coinRepository.findCoinBalanceAndPriceByUid(uid).stream()
                .mapToLong(row -> (long) (convertToDouble(row[0]) * convertToDouble(row[1]))).sum();
    }

    // 채권 자산 합계
    private long sumBondAssets(Optional<Member> uid) {
        return bondRepository.findAllByUidAndDeleteDateIsNull(uid).stream()
                .mapToLong(bond -> (long) bond.getPrice() * bond.getCnt()).sum();
    }

    // Spot 자산 합산 메서드
    private long calculateSpotAssetsSum(Optional<Member> uid) {
        return uid.map(member -> {
            List<Spot> spotData = spotRepository.findByUidAndDeleteDateIsNull(member);
            return spotData.stream().mapToLong(Spot::getPrice).sum();
        }).orElse(0L);
    }

    // 월별 금융 자산 합산 메서드
    private long calculateMonthlyAssetsSum(Member uid, int monthsAgo) {


        LocalDateTime endOfMonthLDT = LocalDate.now().minusMonths(monthsAgo).withDayOfMonth(1).atStartOfDay();
        Date endOfMonth = Date.from(endOfMonthLDT.atZone(ZoneId.systemDefault()).toInstant());

        long monthlySum = 0;

        // 은행 자산 합산
        List<UserBank> bankAssets = bankRepository.findAllByUidAndAddDateBefore(uid, endOfMonth);
        monthlySum += bankAssets.stream().mapToLong(UserBank::getBalanceAmt).sum();

        // 채권 자산 합산
        List<UserBond> bondAssets = bondRepository.findAllByUidAndAddDateBefore(uid,monthsAgo);
        monthlySum += bondAssets.stream().mapToLong(UserBond -> (long) UserBond.getPrice() * UserBond.getCnt()).sum();

        // 가상화폐 자산 합산
        List<Object[]> coinData = coinRepository.findCoinBalanceAndPriceByUserIdAndBefore(uid, endOfMonth, monthsAgo);
        monthlySum += coinData.stream()
                .mapToLong(row -> (long) ((double) row[0] * Integer.parseInt((String) row[1])))
                .sum();

        // 주식 자산 합산
        List<Object[]> stockData = stockRepository.getStockBalanceAndClosingPriceBefore(uid, monthsAgo);
        monthlySum += stockData.stream()
                .mapToLong(row -> (long) ((double) row[0] * (double) row[1]))
                .sum();

        return monthlySum;
    }

    // Spot 자산 월별 합산 메서드
    private long calculateSpotAssetsSumBefore(Member uid, int monthsAgo) {
        LocalDateTime endOfMonthLDT = LocalDate.now().minusMonths(monthsAgo).withDayOfMonth(1).atStartOfDay();
        Date endOfMonth = Date.from(endOfMonthLDT.atZone(ZoneId.systemDefault()).toInstant());
        List<Spot> spotAssets = spotRepository.findByUidAndAddDateBefore(uid, endOfMonth);
        return spotAssets.stream().mapToLong(Spot::getPrice).sum();
    }

    public List<MonthlyBalanceDTO> getMonthlyIncomeOutcomeBalance(int uid) {
        List<Object[]> incomeResults = incomeRepository.findMonthlyIncomeByUid(uid);
        List<Object[]> outcomeResults = outComeUserRepository.findMonthlyOutcomeByUid(uid);

        Map<String, MonthlyBalanceDTO> balanceMap = new HashMap<>();

        for (Object[] income : incomeResults) {
            String month = (String) income[0];
            BigDecimal totalIncome = (BigDecimal) income[1];
            balanceMap.put(month, new MonthlyBalanceDTO(month, totalIncome, BigDecimal.ZERO, totalIncome));
        }

        for (Object[] outcome : outcomeResults) {
            String month = (String) outcome[0];
            BigDecimal totalOutcome = (BigDecimal) outcome[1];
            if (balanceMap.containsKey(month)) {
                MonthlyBalanceDTO dto = balanceMap.get(month);
                dto.setTotalOutcome(totalOutcome);
                dto.setBalance(dto.getTotalIncome().subtract(totalOutcome));
            } else {
                balanceMap.put(month, new MonthlyBalanceDTO(month, BigDecimal.ZERO, totalOutcome, totalOutcome.negate()));
            }
        }

        return new ArrayList<>(balanceMap.values());
    }

}