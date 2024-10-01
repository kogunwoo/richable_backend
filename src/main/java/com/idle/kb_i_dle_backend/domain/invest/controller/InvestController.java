package com.idle.kb_i_dle_backend.domain.invest.controller;

import com.idle.kb_i_dle_backend.common.dto.DataDTO;
import com.idle.kb_i_dle_backend.common.dto.ErrorResponseDTO;
import com.idle.kb_i_dle_backend.common.dto.ResponseDTO;
import com.idle.kb_i_dle_backend.domain.invest.service.InvestService;
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

    // 투자 여유 자금 조회
    @GetMapping("available")
    public ResponseEntity getAvailableAsset() {
        try {
            ResponseDTO response = new ResponseDTO(true,new DataDTO(investService.getInvestList()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(false,e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
