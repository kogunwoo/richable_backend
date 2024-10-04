package com.idle.kb_i_dle_backend.domain.invest.controller;

import com.idle.kb_i_dle_backend.common.dto.ErrorResponseDTO;
import com.idle.kb_i_dle_backend.common.dto.ResponseDTO;
import com.idle.kb_i_dle_backend.domain.invest.dto.*;
import com.idle.kb_i_dle_backend.domain.invest.service.InvestService;
import com.idle.kb_i_dle_backend.domain.member.util.JwtProcessor;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invest")
@Slf4j
@RequiredArgsConstructor
public class InvestController {

    private final InvestService investService;
    private final JwtProcessor jwtProcessor;

    // 공통 메서드로 UID 가져오기
    private Integer getUidFromSession(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Integer uid = jwtProcessor.getUid(token);
        return uid;
    }

    // 투자 여유 자산 조회
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableAsset(HttpServletRequest request) {
        Integer uid = getUidFromSession(request);
        try {
            AvailableCashDTO availableCashDTO = investService.getAvailableCash(uid);
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
    public ResponseEntity<?> getMaxPercentageCategory(HttpServletRequest request) {
        Integer uid = getUidFromSession(request);
        try {
            MaxPercentageCategoryDTO maxPercentageCategory = investService.getMaxPercentageCategory(uid);
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
    public ResponseEntity<?> getRecommendedProducts(HttpServletRequest request) {
        Integer uid = getUidFromSession(request);
        try {
            List<RecommendedProductDTO> recommendedProducts = investService.getRecommendedProducts(uid);
            ResponseDTO response = new ResponseDTO(true, recommendedProducts);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getRecommendedProducts: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(false,
                    "Failed to retrieve recommended products: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/highreturn/stock")
    public ResponseEntity<?> getHighReturnStock(HttpServletRequest request) {
        Integer uid = getUidFromSession(request);
        try {
            List<HighReturnProductDTO> highReturnProductDTOS = investService.getHighReturnStock(uid);
            ResponseDTO response = new ResponseDTO(true, highReturnProductDTOS);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error in getHighReturnProduct: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(false,
                    "Failed to retrieve high return products: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/highreturn/coin")
    public ResponseEntity<?> getHighReturnCoin(HttpServletRequest request) {
        Integer uid = getUidFromSession(request);
        try {
            List<HighReturnProductDTO> highReturnCoins = investService.getHighReturnCoin(uid);
            ResponseDTO response = new ResponseDTO(true, highReturnCoins);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getHighReturnCoin: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(false,
                    "Failed to retrieve high return coins: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/highreturn")
    public ResponseEntity<?> getHighReturnProducts(HttpServletRequest request) {
        Integer uid = getUidFromSession(request);
        try {
            HighReturnProductsDTO highReturnProducts = investService.getHighReturnProducts(uid);
            ResponseDTO response = new ResponseDTO(true, highReturnProducts.getProducts());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getHighReturnProducts: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(false,
                    "Failed to retrieve high return products: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
