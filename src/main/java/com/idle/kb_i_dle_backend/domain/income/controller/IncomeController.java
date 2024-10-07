package com.idle.kb_i_dle_backend.domain.income.controller;

import com.idle.kb_i_dle_backend.global.dto.ErrorResponseDTO;
import com.idle.kb_i_dle_backend.global.dto.SuccessResponseDTO;
import com.idle.kb_i_dle_backend.domain.income.dto.IncomeDTO;
import com.idle.kb_i_dle_backend.domain.income.service.IncomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/income")
@Slf4j
@RequiredArgsConstructor
public class IncomeController {

    @Autowired
    private final IncomeService incomeService;

    // 소득 항목 리스트 반환
    @GetMapping("/all")
    public ResponseEntity<?> getTotalIncomeList() {
        try {
            SuccessResponseDTO response = new SuccessResponseDTO(true, incomeService.getIncomeList());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO( e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 소득 항목 리스트 반환
    @GetMapping("/detail/{index}")
    public ResponseEntity<?> getIncomeDetail(@PathVariable("index") Integer index) {
        try {
            SuccessResponseDTO response = new SuccessResponseDTO(true, incomeService.getIncomeByIndex(index));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO( e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 새로운 소득 추가
    @PostMapping("/add")
    public ResponseEntity<?> addIncome(@RequestBody IncomeDTO incomeDTO) {
        try {
            SuccessResponseDTO response = new SuccessResponseDTO(true, incomeService.addIncome(incomeDTO));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 소득 수정
    @PutMapping("/update")
    public ResponseEntity<?> updateIncome(@RequestBody IncomeDTO incomeDTO) {
        try {
            SuccessResponseDTO response = new SuccessResponseDTO(true, incomeService.updateIncome(incomeDTO));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO( e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 소득 삭제
    @DeleteMapping("/delete/{index}")
    public ResponseEntity<?> deleteIncome(@PathVariable("index") Integer index) {
        try {
            Map<String, Object> indexData = new HashMap<>();
            indexData.put("index", incomeService.deleteIncomeByUidAndIndex(index));
            SuccessResponseDTO response = new SuccessResponseDTO(true, indexData);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
