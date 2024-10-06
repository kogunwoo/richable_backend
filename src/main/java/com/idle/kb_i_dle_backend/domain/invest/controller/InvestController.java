package com.idle.kb_i_dle_backend.domain.invest.controller;

import com.idle.kb_i_dle_backend.global.dto.ErrorResponseDTO;
import com.idle.kb_i_dle_backend.global.dto.SuccessResponseDTO;
import com.idle.kb_i_dle_backend.domain.invest.dto.*;
import com.idle.kb_i_dle_backend.domain.invest.service.InvestService;
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

    // 투자 여유 자산 조회
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableAsset() {
        try {
            AvailableCashDTO availableCashDTO = investService.getAvailableCash();
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
        try {
            MaxPercentageCategoryDTO maxPercentageCategory = investService.getMaxPercentageCategory();
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
        try {
            List<RecommendedProductDTO> recommendedProducts = investService.getRecommendedProducts();
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
        try {
            List<HighReturnProductDTO> highReturnProductDTOS = investService.getHighReturnStock();
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
        try {
            List<HighReturnProductDTO> highReturnCoins = investService.getHighReturnCoin();
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
        try {
            HighReturnProductsDTO highReturnProducts = investService.getHighReturnProducts();
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
