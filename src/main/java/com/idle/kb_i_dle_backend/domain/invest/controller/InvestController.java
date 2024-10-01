package com.idle.kb_i_dle_backend.domain.invest.controller;

import com.idle.kb_i_dle_backend.common.dto.ErrorResponseDTO;
import com.idle.kb_i_dle_backend.common.dto.ResponseDTO;
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
            ResponseDTO response = new ResponseDTO(true, availableCashDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getAvailableAsset: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(false,
                    "Failed to retrieve available assets: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 성향 조회
    @GetMapping("/tendency")
    public ResponseEntity<?> getMaxPercentageCategory() {
        try {
            MaxPercentageCategoryDTO maxPercentageCategory = investService.getMaxPercentageCategory();
            ResponseDTO response = new ResponseDTO(true, maxPercentageCategory);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getMaxPercentageCategory: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(false,
                    "Failed to retrieve max percentage category: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/recommended")
    public ResponseEntity<?> getRecommendedProducts() {
        try {
            List<RecommendedProductDTO> recommendedProducts = investService.getRecommendedProducts();
            ResponseDTO response = new ResponseDTO(true, recommendedProducts);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getRecommendedProducts: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(false,
                    "Failed to retrieve recommended products: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
