package com.idle.kb_i_dle_backend.domain.finance.service;

import com.idle.kb_i_dle_backend.domain.finance.dto.AssetDTO;
import com.idle.kb_i_dle_backend.domain.finance.dto.BondReturnDTO;
import com.idle.kb_i_dle_backend.domain.finance.dto.CoinReturnDTO;
import com.idle.kb_i_dle_backend.domain.finance.dto.FinancialChangeDTO;
import com.idle.kb_i_dle_backend.domain.finance.dto.FinancialSumDTO;
import com.idle.kb_i_dle_backend.domain.finance.dto.MonthlyBalanceDTO;
import com.idle.kb_i_dle_backend.domain.finance.dto.StockReturnDTO;
import com.idle.kb_i_dle_backend.domain.finance.dto.TotalChangeDTO;
import com.idle.kb_i_dle_backend.domain.finance.entity.AssetSummary;
import com.idle.kb_i_dle_backend.domain.finance.entity.Bond;
import com.idle.kb_i_dle_backend.domain.finance.entity.Coin;
import com.idle.kb_i_dle_backend.domain.finance.entity.Spot;
import com.idle.kb_i_dle_backend.domain.finance.entity.Stock;
import com.idle.kb_i_dle_backend.domain.finance.entity.StockProduct;
import com.idle.kb_i_dle_backend.domain.finance.entity.StockProductPrice;
import com.idle.kb_i_dle_backend.domain.finance.repository.AssetSummaryRepository;
import com.idle.kb_i_dle_backend.domain.finance.repository.BankRepository;
import com.idle.kb_i_dle_backend.domain.finance.repository.BondRepository;
import com.idle.kb_i_dle_backend.domain.finance.repository.CoinRepository;
import com.idle.kb_i_dle_backend.domain.finance.repository.SpotRepository;
import com.idle.kb_i_dle_backend.domain.finance.repository.StockProductPriceRepository;
import com.idle.kb_i_dle_backend.domain.finance.repository.StockPriceRepository;
import com.idle.kb_i_dle_backend.domain.finance.repository.StockProductRepository;
import com.idle.kb_i_dle_backend.domain.finance.repository.StockRepository;
import com.idle.kb_i_dle_backend.domain.income.repository.IncomeRepository;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import com.idle.kb_i_dle_backend.domain.member.service.MemberService;
import com.idle.kb_i_dle_backend.domain.outcome.repository.OutcomeUserRepository;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FinanceServiceImpl implements FinanceService {

    private final BankRepository bankRepository;
    private final BondRepository bondRepository;
    private final CoinRepository coinRepository;
    private final SpotRepository spotRepository;
    private final StockRepository stockRepository;
    private final IncomeRepository incomeRepository;
    private final OutcomeUserRepository outComeUserRepository;
    private final AssetSummaryRepository assetSummaryRepository;
    private final MemberService memberService;
    private final StockPriceRepository stockPriceRepository;
    private final StockProductPriceRepository stockProductPriceRepository;
    private final StockProductRepository stockProductRepository;

    // 소수점 이하 한 자리로 포맷팅할 수 있는 DecimalFormat
    private static final DecimalFormat df = new DecimalFormat("#.#");

    // 금융 자산 합계 계산
    @Override
    public FinancialSumDTO getFinancialAssetsSum(int uid) {
        Member member = memberService.findMemberByUid(uid);
        Long financialAssetsSum = assetSummaryRepository.findLatestByUidZeroMonthAgo(member).getTotalAmount();
        return new FinancialSumDTO(financialAssetsSum);
    }

    // 총 자산 합계 계산 (금융 자산 + Spot 자산)
    @Override
    public FinancialSumDTO getTotalAssetsSum(int uid) {
        Member member = memberService.findMemberByUid(uid);
        Long totalAssetsSum =
                assetSummaryRepository.findLatestByUidZeroMonthAgo(member).getTotalAmount() + calculateSpotAssetsSum(member);
        return new FinancialSumDTO(totalAssetsSum);
    }

    @Override
    public FinancialSumDTO getAssetSummeryByDateBefore(int uid, Date date) {
        Member member = memberService.findMemberByUid(uid);
        AssetSummary assetSummary = assetSummaryRepository.findFirstByUidAndUpdateDateBeforeOrderByUpdateDateDesc(
                member, date);
        return new FinancialSumDTO(assetSummary.getTotalAmount());
    }

    // 금융 자산 목록 조회
    @Override
    public List<AssetDTO> getFinancialAsset(int uid) {
        Member member = memberService.findMemberByUid(uid);
        Long sumBankAssets =
                assetSummaryRepository.findLatestByUidZeroMonthAgo(member).getCash() + assetSummaryRepository.findLatestByUidZeroMonthAgo(member).getSaving() +
                        assetSummaryRepository.findLatestByUidZeroMonthAgo(member).getDeposit() +assetSummaryRepository.findLatestByUidZeroMonthAgo(member).getSubscription() +
                        assetSummaryRepository.findLatestByUidZeroMonthAgo(member).getWithdrawal() ;
        List<AssetDTO> assetList = new ArrayList<>();

        assetList.add(new AssetDTO("예적금",sumBankAssets));
        assetList.add(new AssetDTO("주식", assetSummaryRepository.findLatestByUidZeroMonthAgo(member).getStock()));
        assetList.add(new AssetDTO("코인", assetSummaryRepository.findLatestByUidZeroMonthAgo(member).getCoin()));
        assetList.add(new AssetDTO("채권", assetSummaryRepository.findLatestByUidZeroMonthAgo(member).getBond()));

        return assetList;
    }

    // 6개월 금융 자산 변동 계산
    @Override
    public List<FinancialChangeDTO> getSixMonthFinancialChanges(int uid) {
        Member member = memberService.findMemberByUid(uid);
        Date now = new Date();
        List<FinancialChangeDTO> financialChanges = new ArrayList<>();

        financialChanges.add(new FinancialChangeDTO(1, assetSummaryRepository.findLatestByUidZeroMonthAgo(member).getTotalAmount()));
        financialChanges.add(new FinancialChangeDTO(2, assetSummaryRepository.findLatestByUidOneMonthAgo(member, getDateBeforeMonths(now, 1)).getTotalAmount()));
        financialChanges.add(new FinancialChangeDTO(3, assetSummaryRepository.findLatestByUidTwoMonthAgo(member, getDateBeforeMonths(now, 2)).getTotalAmount()));
        financialChanges.add(new FinancialChangeDTO(4, assetSummaryRepository.findLatestByUidThreeMonthAgo(member, getDateBeforeMonths(now, 3)).getTotalAmount()));
        financialChanges.add(new FinancialChangeDTO(5, assetSummaryRepository.findLatestByUidFourMonthAgo(member, getDateBeforeMonths(now, 4)).getTotalAmount()));
        financialChanges.add(new FinancialChangeDTO(6, assetSummaryRepository.findLatestByUidFiveMonthAgo(member, getDateBeforeMonths(now, 5)).getTotalAmount()));

        return financialChanges;
    }

    @Override
    public List<TotalChangeDTO> getSixMonthTotalChanges(int uid) {
        Member member = memberService.findMemberByUid(uid);
        List<TotalChangeDTO> totalChanges = new ArrayList<>();
        Date now = new Date();

        for (int i = 0; i < 6; i++) {
            long monthlySum = switch (i) {
                case 0 -> assetSummaryRepository.findLatestByUidZeroMonthAgo(member).getTotalAmount() + calculateSpotAssetsSum(member);
                case 1 -> assetSummaryRepository.findLatestByUidOneMonthAgo(member, getDateBeforeMonths(now, 1)).getTotalAmount() + calculateSpotAssetsSumBefore(member, 1);
                case 2 -> assetSummaryRepository.findLatestByUidTwoMonthAgo(member, getDateBeforeMonths(now, 2)).getTotalAmount() + calculateSpotAssetsSumBefore(member, 2);
                case 3 -> assetSummaryRepository.findLatestByUidThreeMonthAgo(member, getDateBeforeMonths(now, 3)).getTotalAmount() + calculateSpotAssetsSumBefore(member, 3);
                case 4 -> assetSummaryRepository.findLatestByUidFourMonthAgo(member, getDateBeforeMonths(now, 4)).getTotalAmount() + calculateSpotAssetsSumBefore(member, 4);
                case 5 -> assetSummaryRepository.findLatestByUidFiveMonthAgo(member, getDateBeforeMonths(now, 5)).getTotalAmount() + calculateSpotAssetsSumBefore(member, 5);
                default -> throw new IllegalStateException("Unexpected value: " + i);
            };
            totalChanges.add(new TotalChangeDTO(i + 1, monthlySum));
        }

        return totalChanges;
    }

    @Override
    public List<StockReturnDTO> getStockReturnTrend(int uid) {
        Member member = memberService.findMemberByUid(uid);
        List<StockReturnDTO> stockReturns = new ArrayList<>();
        List<Stock> stocks = stockRepository.findAllByUidAndDeleteDateIsNull(member);

        for (int i = 0; i < 6; i++) {
            double totalStockReturn = 0;
            int stockCount = 0;

            for (Stock stock : stocks) {
                StockProduct stockProduct = stockProductRepository.findByShortCode(String.valueOf(stock.getPdno()));

                double currentPrice;
                if (i == 0) {
                    // 현재 가격 (StockProduct의 price 사용)
                    currentPrice = stockProduct.getPrice() != null ? stockProduct.getPrice() : 0;
                } else {
                    // i개월 전 가격
                    currentPrice = getPriceForMonth(stockProduct.getStockProductPrice(), i);
                }

                double purchasePrice = stock.getAvgBuyPrice();

                if (purchasePrice > 0 && currentPrice > 0) {
                    double stockReturn = ((currentPrice / purchasePrice) * 100) - 100;
                    totalStockReturn += stockReturn;
                    stockCount++;
                }
            }

            if (stockCount > 0) {
                double averageStockReturn = totalStockReturn / stockCount;
                stockReturns.add(new StockReturnDTO(i + 1, Double.parseDouble(df.format(averageStockReturn))));
            } else {
                stockReturns.add(new StockReturnDTO(i + 1, 0));
            }
        }

        return stockReturns;
    }

    private double getPriceForMonth(StockProductPrice stockProductPrice, int monthsAgo) {
        return switch (monthsAgo) {
            case 1 -> stockProductPrice.getOneMonthAgoPrice() != null ? stockProductPrice.getOneMonthAgoPrice() : 0;
            case 2 -> stockProductPrice.getTwoMonthsAgoPrice() != null ? stockProductPrice.getTwoMonthsAgoPrice() : 0;
            case 3 -> stockProductPrice.getThreeMonthsAgoPrice() != null ? stockProductPrice.getThreeMonthsAgoPrice() : 0;
            case 4 -> stockProductPrice.getFourMonthsAgoPrice() != null ? stockProductPrice.getFourMonthsAgoPrice() : 0;
            case 5 -> stockProductPrice.getFiveMonthsAgoPrice() != null ? stockProductPrice.getFiveMonthsAgoPrice() : 0;
            default -> 0;
        };
    }

    //6개월 코인 수익률
    @Override
    public List<CoinReturnDTO> getCoinReturnTrend(int uid) {
        List<CoinReturnDTO> coinReturns = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.##");
        Member member = memberService.findMemberByUid(uid);

        for (int i = 1; i < 7; i++) {
            double totalCoinReturn = 0;
            int coinCount = 0;

            List<Coin> coins = coinRepository.findByUid(member);

            for (Coin coin : coins) {
                Date purchaseDateAsDate = coin.getAddDate();
                LocalDate purchaseDate = purchaseDateAsDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentMonthDate = LocalDate.now().minusMonths(i);
                Date endDate = Date.from(currentMonthDate.withDayOfMonth(currentMonthDate.lengthOfMonth())
                        .atStartOfDay(ZoneId.systemDefault()).toInstant());

                if (i == 1) {
                    // 현재 가격과 매입 가격 비교하여 수익률 계산
                    double currentPrice = 30000; // 임의로 설정된 현재 가격
                    double purchasePrice = 0.0;
                    // null인 경우 처리 로직
                    if (coinRepository.findCoinPriceBy(coin.getCurrency()) == null) {
                        purchasePrice = 0.0; // 또는 다른 적절한 기본값
                    } else {
                        coinRepository.findCoinPriceBy(coin.getCurrency());
                    }

                    if (purchasePrice > 0) {
                        double coinReturn = ((currentPrice / purchasePrice) * 100) - 100;
                        totalCoinReturn += coinReturn;
                        coinCount++;
                    }
                } else {
                    // 특정 달의 가격으로 수익률 계산
                    if (!purchaseDate.isAfter(currentMonthDate)) {
                        Double currentMonthPrice = coinRepository.getCoinPriceForMonth(coin.getCurrency(), i);
                        double purchasePrice = 0.0;
                        if (coinRepository.findCoinPriceBy(coin.getCurrency()) == null) {
                            // 예: 기본값 설정, 로그 기록, 또는 예외 처리
                            purchasePrice = 0.0; // 또는 다른 적절한 기본값
                        } else {
                            coinRepository.findCoinPriceBy(coin.getCurrency());
                        }

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
        Member member = memberService.findMemberByUid(uid);

        for (int i = 1; i < 7; i++) {
            double totalBondReturn = 0;
            int bondCount = 0;

            List<Bond> bonds = bondRepository.findByUid(member);

            for (Bond bond : bonds) {
                Date purchaseDateAsDate = bond.getAddDate();
                LocalDate purchaseDate = purchaseDateAsDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentMonthDate = LocalDate.now().minusMonths(i);
                Date endDate = Date.from(currentMonthDate.withDayOfMonth(currentMonthDate.lengthOfMonth())
                        .atStartOfDay(ZoneId.systemDefault()).toInstant());

                if (i == 1) {
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

    // Spot 자산 합산 메서드
    private long calculateSpotAssetsSum(Member uid) {
        List<Spot> spotData = spotRepository.findByUidAndDeleteDateIsNull(uid);
        return spotData.stream().mapToLong(Spot::getPrice).sum();
    }

    // Spot 자산 월별 합산 메서드
    private long calculateSpotAssetsSumBefore(Member uid, int monthsAgo) {
        List<Object[]> spotAssets = spotRepository.findMonthlySpotAssets(uid);
        return spotAssets.stream()
                .filter(row -> (int) row[0] == monthsAgo)  // month 필터링
                .mapToLong(row -> ((long) row[1]))  // totalAmount 계산
                .sum();
    }

    public List<MonthlyBalanceDTO> getMonthlyIncomeOutcomeBalance(int uid) {
        List<Object[]> incomeResults = incomeRepository.findMonthlyIncomeByUid(uid);
        List<Object[]> outcomeResults = outComeUserRepository.findMonthlyOutcomeByUid(uid);

        Map<String, MonthlyBalanceDTO> balanceMap = new HashMap<>();

        for (Object[] income : incomeResults) {
            String month = (String) income[0];
            long totalIncome = ((BigDecimal) income[1]).longValueExact();
            balanceMap.put(month, new MonthlyBalanceDTO(month, totalIncome, 0L, totalIncome));
        }

        for (Object[] outcome : outcomeResults) {
            String month = (String) outcome[0];
            long totalOutcome = ((BigDecimal) outcome[1]).longValueExact();
            if (balanceMap.containsKey(month)) {
                MonthlyBalanceDTO dto = balanceMap.get(month);
                dto.setTotalOutcome(totalOutcome);
                dto.setBalance(dto.getTotalIncome() - totalOutcome);
            } else {
                balanceMap.put(month,
                        new MonthlyBalanceDTO(month, 0L, totalOutcome, totalOutcome));
            }
        }

        return new ArrayList<>(balanceMap.values());
    }

    public Map<String, Object> compareAssetsWithAgeGroup(int uid) {

        // 1. 현재 사용자의 uid를 기반으로 나이 정보 추출
        Member member = memberService.findMemberByUid(uid);

        int birthYear = member.getBirth_year();

        // 2. 10대, 20대 구분 (예: 10대는 2000~2009년 출생, 20대는 1990~1999년 출생)
        int lowerBoundYear = birthYear - (birthYear % 10); // 연령대 시작 연도
        int upperBoundYear = lowerBoundYear + 9; // 연령대 끝 연도

        // 3. 해당 연령대의 평균 자산 구하기
        Long avgAmount = assetSummaryRepository.findAverageAmountByAgeRange(lowerBoundYear, upperBoundYear);

        // 4. 현재 사용자의 자산 정보 구하기
        AssetSummary userAssetSummary = assetSummaryRepository.findLatestByUid(member);
        Long userTotalAmount = userAssetSummary.getTotalAmount();

        // 5. 차이 계산
        Long deferAmount = userTotalAmount - avgAmount;

        // 6. 결과값 반환
        Map<String, Object> response = new HashMap<>();
        response.put("bsAmount", userTotalAmount);
        response.put("spotAvgAmount", avgAmount);
        response.put("defer", deferAmount);

        return response;
    }

    @Override
    public List<Map<String, Object>> compareAssetsByCategoryWithAgeGroup(int uid) {
        // 1. 현재 사용자의 uid를 기반으로 나이 정보 추출
        Member member = memberService.findMemberByUid(uid);

        int birthYear = member.getBirth_year();

        // 2. 10대, 20대 구분
        int lowerBoundYear = birthYear - (birthYear % 10);
        int upperBoundYear = lowerBoundYear + 9;

        // 3. 자산 요약 가져오기
        AssetSummary userAssetSummary = assetSummaryRepository.findLatestByUid(member);
        if (userAssetSummary == null) {
            throw new IllegalArgumentException("Asset summary not found for uid: " + uid);
        }

        // 4. 자산 카테고리별로 비교
        List<Map<String, Object>> assetComparisonList = new ArrayList<>();
        addComparisonData("채권", userAssetSummary.getBond(), lowerBoundYear, upperBoundYear, assetComparisonList);
        addComparisonData("예금", userAssetSummary.getDeposit(), lowerBoundYear, upperBoundYear, assetComparisonList);
        addComparisonData("적금", userAssetSummary.getSaving(), lowerBoundYear, upperBoundYear, assetComparisonList);
        addComparisonData("청약", userAssetSummary.getSubscription(), lowerBoundYear, upperBoundYear,
                assetComparisonList);
        addComparisonData("입출금", userAssetSummary.getWithdrawal(), lowerBoundYear, upperBoundYear, assetComparisonList);
        addComparisonData("주식", userAssetSummary.getStock(), lowerBoundYear, upperBoundYear, assetComparisonList);
        addComparisonData("현금", userAssetSummary.getCash(), lowerBoundYear, upperBoundYear, assetComparisonList);
        addComparisonData("코인", userAssetSummary.getCoin(), lowerBoundYear, upperBoundYear, assetComparisonList);

        return assetComparisonList;
    }

    private void addComparisonData(String category, Long bsAmount, int lowerBoundYear, int upperBoundYear,
                                   List<Map<String, Object>> assetComparisonList) {
        if (bsAmount == null) {
            bsAmount = 0L;
        }

        // 연령대의 평균 자산 구하기
        Long avgAmount = assetSummaryRepository.findAverageAmountByAgeRange(lowerBoundYear, upperBoundYear);
        Long deferAmount = bsAmount - avgAmount;

        // 각 카테고리별 결과값 추가
        Map<String, Object> comparisonData = new HashMap<>();
        comparisonData.put("bsAmount", bsAmount);
        comparisonData.put("spotAvgAmount", avgAmount);
        comparisonData.put("defer", deferAmount);
        comparisonData.put("category", category);

        assetComparisonList.add(comparisonData);
    }
    private Date getDateBeforeMonths(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -months);
        return cal.getTime();
    }


}