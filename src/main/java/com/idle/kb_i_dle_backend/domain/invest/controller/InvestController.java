package com.idle.kb_i_dle_backend.domain.invest.controller;

import com.idle.kb_i_dle_backend.domain.invest.dto.AvailableCashDTO;
import com.idle.kb_i_dle_backend.domain.invest.dto.HighReturnProductDTO;
import com.idle.kb_i_dle_backend.domain.invest.dto.HighReturnProductsDTO;
import com.idle.kb_i_dle_backend.domain.invest.dto.MaxPercentageCategoryDTO;
import com.idle.kb_i_dle_backend.domain.invest.dto.RecommendedProductDTO;
import com.idle.kb_i_dle_backend.domain.invest.service.InvestService;
import com.idle.kb_i_dle_backend.domain.member.service.MemberService;
import com.idle.kb_i_dle_backend.global.dto.ErrorResponseDTO;
import com.idle.kb_i_dle_backend.global.dto.SuccessResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invest")
@Slf4j
@RequiredArgsConstructor
public class InvestController {

    private final InvestService investService;
    private final MemberService memberService;

    // 투자 여유 자산 조회
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableAsset() {
        Integer uid = memberService.getCurrentUid();
        log.info("check uid in invest" + uid);
        try {
            AvailableCashDTO availableCashDTO = investService.getAvailableCash(uid);
            SuccessResponseDTO response = new SuccessResponseDTO(true, availableCashDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getAvailableAsset: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(
                    "Failed to retrieve available assets: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 성향 조회
    @GetMapping("/tendency")
    public ResponseEntity<?> getMaxPercentageCategory() {
        Integer uid = memberService.getCurrentUid();
        try {
            MaxPercentageCategoryDTO maxPercentageCategory = investService.getMaxPercentageCategory(uid);
            SuccessResponseDTO response = new SuccessResponseDTO(true, maxPercentageCategory);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getMaxPercentageCategory: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(
                    "Failed to retrieve max percentage category: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/recommended")
    public ResponseEntity<?> getRecommendedProducts() {
        Integer uid = memberService.getCurrentUid();
        try {
            List<RecommendedProductDTO> recommendedProducts = investService.getRecommendedProducts(uid);
            SuccessResponseDTO response = new SuccessResponseDTO(true, recommendedProducts);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getRecommendedProducts: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(
                    "Failed to retrieve recommended products: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/highreturn/stock")
    public ResponseEntity<?> getHighReturnStock() {
        Integer uid = memberService.getCurrentUid();
        try {
            List<HighReturnProductDTO> highReturnProductDTOS = investService.getHighReturnStock(uid);
            SuccessResponseDTO response = new SuccessResponseDTO(true, highReturnProductDTOS);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error in getHighReturnProduct: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(
                    "Failed to retrieve high return products: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/highreturn/coin")
    public ResponseEntity<?> getHighReturnCoin() {
        Integer uid = memberService.getCurrentUid();
        try {
            List<HighReturnProductDTO> highReturnCoins = investService.getHighReturnCoin(uid);
            SuccessResponseDTO response = new SuccessResponseDTO(true, highReturnCoins);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getHighReturnCoin: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(
                    "Failed to retrieve high return coins: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/highreturn")
    public ResponseEntity<?> getHighReturnProducts() {
        Integer uid = memberService.getCurrentUid();
        try {
            HighReturnProductsDTO highReturnProducts = investService.getHighReturnProducts(uid);
            SuccessResponseDTO response = new SuccessResponseDTO(true, highReturnProducts.getProducts());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getHighReturnProducts: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(
                    "Failed to retrieve high return products: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
