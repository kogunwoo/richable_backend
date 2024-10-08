package com.idle.kb_i_dle_backend.domain.finance.controller;

import com.idle.kb_i_dle_backend.domain.finance.dto.SpotDTO;
import com.idle.kb_i_dle_backend.domain.finance.service.SpotService;
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
@RequestMapping("/finance")
@Slf4j
@RequiredArgsConstructor
public class SpotController {

    private final SpotService spotService;  // SpotServiceImpl 대신 SpotService 인터페이스로 주입
    private final MemberService memberService;


    // 카테고리에 따른 총 가격 반환
    @GetMapping("/spot/{category}/sum")
    public ResponseEntity<?> getTotalPriceByCategory(@PathVariable("category") String category) {
        try {
            Integer uid = memberService.getCurrentUid();
            SuccessResponseDTO response = new SuccessResponseDTO(true,
                    spotService.getTotalPriceByCategory(uid, category));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    // 현물 자산 총 가격 반환
    @GetMapping("/spot/sum")
    public ResponseEntity<?> getTotalPriceByCategory() {
        try {
            Integer uid = memberService.getCurrentUid();
            SuccessResponseDTO response = new SuccessResponseDTO(true, spotService.getTotalPrice(uid));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 현물 자산 리스트 반환
    @GetMapping("/spot/all")
    public ResponseEntity<?> getTotalSpotList() {
        try {
            Integer uid = memberService.getCurrentUid();
            SuccessResponseDTO response = new SuccessResponseDTO(true, spotService.getSpotList(uid));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 새로운 Spot 추가
    @PostMapping("/spot/add")
    public ResponseEntity<?> addSpot(@RequestBody SpotDTO spotDTO) {
        try {
            Integer uid = memberService.getCurrentUid();
            SuccessResponseDTO response = new SuccessResponseDTO(true, spotService.addSpot(uid, spotDTO));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Spot 수정
    @PutMapping("/spot/update")
    public ResponseEntity<?> updateSpot(@RequestBody SpotDTO spotDTO) {
        try {
            Integer uid = memberService.getCurrentUid();
            SuccessResponseDTO response = new SuccessResponseDTO(true, spotService.updateSpot(uid, spotDTO));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Spot 삭제
    @DeleteMapping("/spot/delete/{index}")
    public ResponseEntity<?> deleteSpot(@PathVariable("index") Integer index) {
        try {
            Integer uid = memberService.getCurrentUid();
            Map<String, Object> indexData = new HashMap<>();
            indexData.put("index", spotService.deleteSpot(uid, index).getIndex());
            SuccessResponseDTO response = new SuccessResponseDTO(true, indexData);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
