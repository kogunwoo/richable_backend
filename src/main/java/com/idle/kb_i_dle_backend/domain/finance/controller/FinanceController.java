package com.idle.kb_i_dle_backend.domain.finance.controller;

import com.idle.kb_i_dle_backend.common.dto.ResponseDTO;
import com.idle.kb_i_dle_backend.domain.finance.dto.*;
import com.idle.kb_i_dle_backend.domain.finance.service.FinanceService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.idle.kb_i_dle_backend.domain.income.repository.IncomeRepository;
import com.idle.kb_i_dle_backend.domain.member.util.JwtProcessor;
import com.idle.kb_i_dle_backend.domain.outcome.repository.OutcomeUserRepository;
import com.idle.kb_i_dle_backend.domain.outcome.service.OutcomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/finance")
@Slf4j
@RequiredArgsConstructor
public class FinanceController {

    @Autowired
    private final FinanceService financeService;


    private final JwtProcessor jwtProcessor;

    // 공통 메서드로 UID 가져오기
    private Integer getUidFromSession(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Integer uid = jwtProcessor.getUid(token);
        return uid;
    }

    // AS_1 금융 자산 합 조회
    // // 토큰 값 불러오는 방식 구현 완료
    @GetMapping("/fin/sum")
    public ResponseEntity<ResponseDTO> getFinancialAssetsSum(HttpServletRequest request) {
        Integer uid = getUidFromSession(request);

        if (uid == null) {
            throw new IllegalArgumentException("UID is missing==============================================");
        }
        FinancialSumDTO totalPrice = financeService.getFinancialAssetsSum(uid);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", totalPrice);

        ResponseDTO Response = new ResponseDTO(true, responseData);

        return ResponseEntity.ok(Response);
    }


    // AS_2 금융 +현물 자산 합 조회
    @GetMapping("/fin")
    public ResponseEntity<?> getTotalAsset(HttpServletRequest request) {
        Integer uid = getUidFromSession(request);

        List<AssetDTO> totalPrice = financeService.getFinancialAsset(uid);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", totalPrice);

        ResponseDTO successResponse = new ResponseDTO(true, responseData);

        return ResponseEntity.ok(successResponse);
    }

    // AS_2 금융 +현물 자산 합 조회
    @GetMapping("/total/sum")
    public ResponseEntity<?> getTotalAssetsSum(HttpServletRequest request) {
        Integer uid = getUidFromSession(request);

        FinancialSumDTO totalPrice = financeService.getTotalAssetsSum(uid);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", totalPrice);

        ResponseDTO successResponse = new ResponseDTO(true, responseData);

        return ResponseEntity.ok(successResponse);
    }


    // AS_5 6개월간 금융 자산 변화 추이
    @GetMapping("/changed/fin")
    public ResponseEntity<?> getSixMonthFinancialChanges(HttpServletRequest request) {
        Integer uid = getUidFromSession(request);

        List<FinancialChangeDTO> result = financeService.getSixMonthFinancialChanges(uid);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", result);

        ResponseDTO successResponse = new ResponseDTO(true, responseData);

        return ResponseEntity.ok(successResponse);
    }

    // AS_6 6개월간 금융 자산 + 현물량 변화 추이
    @GetMapping("/changed/spot")
    public ResponseEntity<?> getSixMonthTotalChanges(HttpServletRequest request) {
        Integer uid = getUidFromSession(request);

        List<TotalChangeDTO> result = financeService.getSixMonthTotalChanges(uid);

        ResponseDTO successResponse = new ResponseDTO(true, result);

        return ResponseEntity.ok(successResponse);
    }

    // AS_7 달별 저축률 추이
    @GetMapping("/return/income")
    public ResponseEntity<?> getMonthlySavingRateTrend(HttpServletRequest request) {
        Integer uid = getUidFromSession(request);
        List<MonthlyBalanceDTO> totalPrice = financeService.getMonthlyIncomeOutcomeBalance(uid);

        ResponseDTO successResponse = new ResponseDTO(true, totalPrice);

        return ResponseEntity.ok(successResponse);
    }

    // AS_8 달별 주식 수익률
    @GetMapping("/return/stock")
    public ResponseEntity<?> getStockReturnTrend(HttpServletRequest request) {
        Integer uid = getUidFromSession(request);
        List<StockReturnDTO> stockReturn = financeService.getStockReturnTrend(uid);

        ResponseDTO successResponse = new ResponseDTO(true, stockReturn);

        return ResponseEntity.ok(successResponse);
    }

    // AS_9 달별 가상화폐 수익률
    @GetMapping("/return/coin")
    public ResponseEntity<?> getCoinReturnTrend(HttpServletRequest request) {
        Integer uid = getUidFromSession(request);
        List<CoinReturnDTO> coinReturn = financeService.getCoinReturnTrend(uid);

        ResponseDTO successResponse = new ResponseDTO(true, coinReturn);

        return ResponseEntity.ok(successResponse);
    }

    // AS_10 달별 채권 수익률
    @GetMapping("/return/bond")
    public ResponseEntity<?> getBondReturnTrend(HttpServletRequest request) {
        Integer uid = getUidFromSession(request);
        List<BondReturnDTO> bondReturn = financeService.getBondReturnTrend(uid);

        ResponseDTO successResponse = new ResponseDTO(true, bondReturn);

        return ResponseEntity.ok(successResponse);
    }

    // AS_11,12
    @GetMapping("/peer")
    @ResponseBody
    public ResponseEntity<?> compareAssetsWithSameAgeGroup(@RequestParam("uid") int uid) {
        try {
            // 같은 나이대 자산 비교 결과 가져오기
            AssetComparisonDTO assetComparison = (AssetComparisonDTO) financeService.compareAssetsWithSameAgeGroup(uid);

            // 응답 데이터 구성
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("bsAmount", assetComparison.getBsAmount());
            responseData.put("spotAvgAmount", assetComparison.getSpotAvgAmount());
            responseData.put("defer", assetComparison.getDefer());

            ResponseDTO successResponse = new ResponseDTO(true, responseData);

            // 성공적인 응답 반환
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(false, "에러가 발생했습니다: " + e.getMessage()));
        }
    }


}
