package com.idle.kb_i_dle_backend.consume.controller;

import com.idle.kb_i_dle_backend.consume.dto.OutcomeAverageDTO;
import com.idle.kb_i_dle_backend.consume.dto.OutcomeUserDTO;
import com.idle.kb_i_dle_backend.consume.service.ConsumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consume")
@Slf4j
@RequiredArgsConstructor
public class ConsumeController {

    private final ConsumeService consumeService;

    //전체조회
    @GetMapping("/")
    public ResponseEntity<List<OutcomeAverageDTO>> getAll() {
        List<OutcomeAverageDTO> outcomeAverage = consumeService.getAll();
        return ResponseEntity.ok(outcomeAverage);
    }

    @GetMapping("/detail")
    public ResponseEntity<List<OutcomeUserDTO>> getAllUser() {
        List<OutcomeUserDTO> outcomeUser = consumeService.getAllUser();
        return ResponseEntity.ok(outcomeUser);
    }

//    // 소비 상세 조회: /detail
//    @GetMapping("/detail")
//    public ResponseEntity<List<OutcomeAverageDTO>> getAll() {
//        List<OutcomeAverageDTO> outcomeAverage = consumeService.getAll();
//        return ResponseEntity.ok(outcomeAverage);
//    }

//    // 달, 카테고리별 소비 합계: /category/sum
//    @GetMapping("/category/sum")
//    public ResponseEntity<Map<String, Long>> getCategorySum(@RequestParam int outcome) {
//        Map<String, Long> categorySum = consumeService.getExpCategory(outcome);
//        return ResponseEntity.ok(categorySum);
//    }
//
//    // 달의 일자별 소비 조회: /category/dailysum
//    @GetMapping("/category/dailysum")
//    public ResponseEntity<Map<String, Long>> getDailySum(@RequestParam String month) {
//        Map<String, Long> dailySum = consumeService.getDailySum(month);
//        return ResponseEntity.ok(dailySum);
//    }
//
//    // 평균 대비 카테고리 별 소비량: /compare
//    @GetMapping("/compare")
//    public ResponseEntity<Map<String, Long>> compareWithAverage(@RequestParam int uid, @RequestParam String month) {
//        Map<String, Long> comparison = consumeService.compareWithAverage(uid, month);
//        return ResponseEntity.ok(comparison);
    }


