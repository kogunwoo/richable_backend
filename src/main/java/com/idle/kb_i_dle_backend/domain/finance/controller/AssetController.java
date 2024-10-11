package com.idle.kb_i_dle_backend.domain.finance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idle.kb_i_dle_backend.domain.finance.dto.BankDTO;
import com.idle.kb_i_dle_backend.domain.finance.dto.BondDTO;
import com.idle.kb_i_dle_backend.domain.finance.dto.CoinDTO;
import com.idle.kb_i_dle_backend.domain.finance.dto.StockDTO;
import com.idle.kb_i_dle_backend.domain.finance.service.BankService;
import com.idle.kb_i_dle_backend.domain.finance.service.BondService;
import com.idle.kb_i_dle_backend.domain.finance.service.CoinService;
import com.idle.kb_i_dle_backend.domain.finance.service.StockService;
import com.idle.kb_i_dle_backend.domain.member.service.MemberService;
import com.idle.kb_i_dle_backend.global.dto.ErrorResponseDTO;
import com.idle.kb_i_dle_backend.global.dto.SuccessResponseDTO;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/asset")
@Slf4j
@RequiredArgsConstructor
public class AssetController {
    private final BankService bankService;
    private final BondService bondService;
    private final CoinService coinService;
    private final StockService stockService;
    private final MemberService memberService;

    // asset 리스트 반환
    @GetMapping("/{category}/all")
    public ResponseEntity<?> getAssetList(@PathVariable("category") String category) {

        try {
            Integer uid = memberService.getCurrentUid();
            if (category.equals("bank")) {
                SuccessResponseDTO response = new SuccessResponseDTO(true, bankService.getBankList(uid));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if (category.equals("bond")) {
                SuccessResponseDTO response = new SuccessResponseDTO(true, bondService.getBondList(uid));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if (category.equals("coin")) {
                SuccessResponseDTO response = new SuccessResponseDTO(true, coinService.getCoinList(uid));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if (category.equals("stock")) {
                SuccessResponseDTO response = new SuccessResponseDTO(true, stockService.getStockList(uid));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ErrorResponseDTO response = new ErrorResponseDTO("카테고리가 제대로 입력되지 않았습니다.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{category}/add")
    public ResponseEntity<?> addAsset(@PathVariable("category") String category, @RequestBody String reqBody) {
        try {
            Integer uid = memberService.getCurrentUid();
            if (category.equals("bank")) {
                BankDTO bankData = new ObjectMapper().readValue(reqBody, BankDTO.class);
                SuccessResponseDTO response = new SuccessResponseDTO(true, bankService.addBank(uid, bankData));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if (category.equals("bond")) {
                BondDTO bondData = new ObjectMapper().readValue(reqBody, BondDTO.class);
                SuccessResponseDTO response = new SuccessResponseDTO(true, bondService.addBond(uid, bondData));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if (category.equals("coin")) {
                CoinDTO coinData = new ObjectMapper().readValue(reqBody, CoinDTO.class);
                SuccessResponseDTO response = new SuccessResponseDTO(true, coinService.addCoin(uid, coinData));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if (category.equals("stock")) {
                StockDTO stockData = new ObjectMapper().readValue(reqBody, StockDTO.class);
                SuccessResponseDTO response = new SuccessResponseDTO(true, stockService.addStock(uid, stockData));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ErrorResponseDTO response = new ErrorResponseDTO("카테고리가 제대로 입력되지 않았습니다.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Asset 수정
    @PutMapping("/{category}/update")
    public ResponseEntity<?> updateAsset(@PathVariable("category") String category, @RequestBody String reqBody) {
        try {
            Integer uid = memberService.getCurrentUid();
            if (category.equals("bank")) {
                BankDTO bankData = new ObjectMapper().readValue(reqBody, BankDTO.class);
                SuccessResponseDTO response = new SuccessResponseDTO(true, bankService.updateBank(uid, bankData));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if (category.equals("bond")) {
                BondDTO bondData = new ObjectMapper().readValue(reqBody, BondDTO.class);
                SuccessResponseDTO response = new SuccessResponseDTO(true, bondService.updateBond(uid, bondData));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if (category.equals("coin")) {
                CoinDTO coinData = new ObjectMapper().readValue(reqBody, CoinDTO.class);
                SuccessResponseDTO response = new SuccessResponseDTO(true, coinService.updateCoin(uid, coinData));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if (category.equals("stock")) {
                StockDTO stockData = new ObjectMapper().readValue(reqBody, StockDTO.class);
                SuccessResponseDTO response = new SuccessResponseDTO(true, stockService.updateStock(uid, stockData));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ErrorResponseDTO response = new ErrorResponseDTO("카테고리가 제대로 입력되지 않았습니다.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Asset 삭제
    @DeleteMapping("/{category}/delete/{index}")
    public ResponseEntity<?> updateAsset(@PathVariable("category") String category,
                                         @PathVariable("index") Integer index) {
        try {
            Integer uid = memberService.getCurrentUid();
            if (category.equals("bank")) {
                Map<String, Object> indexData = new HashMap<>();
                indexData.put("index", bankService.deleteBank(uid, index).getIndex());
                SuccessResponseDTO response = new SuccessResponseDTO(true, indexData);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if (category.equals("bond")) {
                Map<String, Object> indexData = new HashMap<>();
                indexData.put("index", bondService.deleteBond(uid, index).getIndex());
                SuccessResponseDTO response = new SuccessResponseDTO(true, indexData);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if (category.equals("coin")) {
                Map<String, Object> indexData = new HashMap<>();
                indexData.put("index", coinService.deleteCoin(uid, index).getIndex());
                SuccessResponseDTO response = new SuccessResponseDTO(true, indexData);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if (category.equals("stock")) {
                Map<String, Object> indexData = new HashMap<>();
                indexData.put("index", stockService.deleteStock(uid, index).getIndex());
                SuccessResponseDTO response = new SuccessResponseDTO(true, indexData);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ErrorResponseDTO response = new ErrorResponseDTO("카테고리가 제대로 입력되지 않았습니다.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 입출금 계좌 조회
    @GetMapping("/account/list")
    public ResponseEntity<?> listOfAccount() {
        try {
            Integer uid = memberService.getCurrentUid();
            Map<String, Object> accountData = new HashMap<>();
            accountData.put("account", bankService.getAccount(uid));
            SuccessResponseDTO response = new SuccessResponseDTO(true, accountData);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
