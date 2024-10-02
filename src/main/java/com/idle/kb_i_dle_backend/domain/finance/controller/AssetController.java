package com.idle.kb_i_dle_backend.domain.finance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idle.kb_i_dle_backend.common.dto.ErrorResponseDTO;
import com.idle.kb_i_dle_backend.common.dto.ResponseDTO;
import com.idle.kb_i_dle_backend.domain.finance.dto.BankDTO;
import com.idle.kb_i_dle_backend.domain.finance.dto.BondDTO;
import com.idle.kb_i_dle_backend.domain.finance.dto.CoinDTO;
import com.idle.kb_i_dle_backend.domain.finance.dto.StockDTO;
import com.idle.kb_i_dle_backend.domain.finance.service.BankService;
import com.idle.kb_i_dle_backend.domain.finance.service.BondService;

import com.idle.kb_i_dle_backend.domain.finance.service.CoinService;
import com.idle.kb_i_dle_backend.domain.finance.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/asset")
@Slf4j
@RequiredArgsConstructor
public class AssetController {
    private final BankService bankService;
    private final BondService bondService;
    private final CoinService coinService;
    private final StockService stockService;

    // asset 리스트 반환
    @GetMapping("/{category}/all")
    public ResponseEntity<?> getAssetList(@PathVariable("category") String category) {
        try {
            if(category.equals("bank")) {
                ResponseDTO response = new ResponseDTO(true, bankService.getBankList());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else if(category.equals("bond")) {
                ResponseDTO response = new ResponseDTO(true, bondService.getBondList());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else if(category.equals("coin")) {
                ResponseDTO response = new ResponseDTO(true, coinService.getCoinList());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else if(category.equals("stock")) {
                ResponseDTO response = new ResponseDTO(true, stockService.getStockList());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else {
                ErrorResponseDTO response = new ErrorResponseDTO(false, "카테고리가 제대로 입력되지 않았습니다.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(false, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{category}/add")
    public ResponseEntity<?> addAsset(@PathVariable("category") String category, @RequestBody String reqBody) {
        try {
            if(category.equals("bank")) {
                BankDTO bankData = new ObjectMapper().readValue(reqBody, BankDTO.class);
                ResponseDTO response = new ResponseDTO(true, bankService.addBank(bankData));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else if(category.equals("bond")) {
                BondDTO bondData = new ObjectMapper().readValue(reqBody, BondDTO.class);
                ResponseDTO response = new ResponseDTO(true, bondService.addBond(bondData));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else if(category.equals("coin")) {
                CoinDTO coinData = new ObjectMapper().readValue(reqBody, CoinDTO.class);
                ResponseDTO response = new ResponseDTO(true, coinService.addCoin(coinData));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else if(category.equals("stock")) {
                StockDTO stockData = new ObjectMapper().readValue(reqBody, StockDTO.class);
                ResponseDTO response = new ResponseDTO(true, stockService.addStock(stockData));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else {
                ErrorResponseDTO response = new ErrorResponseDTO(false, "카테고리가 제대로 입력되지 않았습니다.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(false, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Asset 수정
    @PutMapping("/{category}/update")
    public ResponseEntity<?> updateAsset(@PathVariable("category") String category, @RequestBody String reqBody) {
        try {
            if(category.equals("bank")) {
                BankDTO bankData = new ObjectMapper().readValue(reqBody, BankDTO.class);
                ResponseDTO response = new ResponseDTO(true, bankService.updateBank(bankData));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else if(category.equals("bond")) {
                BondDTO bondData = new ObjectMapper().readValue(reqBody, BondDTO.class);
                ResponseDTO response = new ResponseDTO(true, bondService.updateBond(bondData));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else if(category.equals("coin")) {
                CoinDTO coinData = new ObjectMapper().readValue(reqBody, CoinDTO.class);
                ResponseDTO response = new ResponseDTO(true, coinService.updateCoin(coinData));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else if(category.equals("stock")) {
                StockDTO stockData = new ObjectMapper().readValue(reqBody, StockDTO.class);
                ResponseDTO response = new ResponseDTO(true, stockService.updateStock(stockData));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else {
                ErrorResponseDTO response = new ErrorResponseDTO(false, "카테고리가 제대로 입력되지 않았습니다.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(false, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Asset 삭제
    @DeleteMapping("/{category}/delete/{index}")
    public ResponseEntity<?> updateAsset(@PathVariable("category") String category, @PathVariable("index") Integer index) {
        try {
            if(category.equals("bank")) {
                Map<String, Object> indexData = new HashMap<>();
                indexData.put("index", bankService.deleteBank(index).getIndex());
                ResponseDTO response = new ResponseDTO(true, indexData);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else if(category.equals("bond")) {
                Map<String, Object> indexData = new HashMap<>();
                indexData.put("index", bondService.deleteBond(index).getIndex());
                ResponseDTO response = new ResponseDTO(true, indexData);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else if(category.equals("coin")) {
                Map<String, Object> indexData = new HashMap<>();
                indexData.put("index", coinService.deleteCoin(index).getIndex());
                ResponseDTO response = new ResponseDTO(true, indexData);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else if(category.equals("stock")) {
                Map<String, Object> indexData = new HashMap<>();
                indexData.put("index", stockService.deleteStock(index).getIndex());
                ResponseDTO response = new ResponseDTO(true, indexData);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else {
                ErrorResponseDTO response = new ErrorResponseDTO(false, "카테고리가 제대로 입력되지 않았습니다.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(false, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
