package com.idle.kb_i_dle_backend.consume.controller;

import com.idle.kb_i_dle_backend.consume.dto.*;
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

    // 전체 소비 데이터 조회
    @GetMapping("/")
    public ResponseEntity<List<OutcomeAverageDTO>> getAll() {
        List<OutcomeAverageDTO> outcomeAverage = consumeService.getAll();
        return ResponseEntity.ok(outcomeAverage);
    }

    // 사용자 소비 상세 조회
    @GetMapping("/detail")
    public ResponseEntity<List<OutcomeUserDTO>> getAllUser() {
        List<OutcomeUserDTO> outcomeUser = consumeService.getAllUser();
        return ResponseEntity.ok(outcomeUser);
    }

//    // 달, 카테고리별 소비 합계 조회
//    @GetMapping("/category/sum")
//    public ResponseEntity<List<CategorySumDTO>> getCategorySum(@RequestParam int uid, @RequestParam int year, @RequestParam int month) {
//        List<CategorySumDTO> categorySum = consumeService.getCategorySum(uid, year, month);
//        return ResponseEntity.ok(categorySum);
//    }

//    // 소비 상세 조회: /detail
//    @GetMapping("/detail")
//    public ResponseEntity<List<OutcomeAverageDTO>> getAll() {
//        List<OutcomeAverageDTO> outcomeAverage = consumeService.getAll();
//        return ResponseEntity.ok(outcomeAverage);
//    }

//    // 달의 일자별 소비 조회: /category/dailysum
//    @GetMapping("/category/dailysum")
//    public ResponseEntity<Map<String, Long>> getDailySum(@RequestParam String month) {
//        Map<String, Long> dailySum = consumeService.getDailySum(month);
//        return ResponseEntity.ok(dailySum);
//    }
//
//    // 평균 대비 카테고리 별 소비량 비교: /compare
//    @GetMapping("/compare")
//    public ResponseEntity<Map<String, Long>> compareWithAverage(@RequestParam int uid, @RequestParam String month) {
//        Map<String, Long> comparison = consumeService.compareWithAverage(uid, month);
//        return ResponseEntity.ok(comparison);
//    }
    @GetMapping("/category/sum/{cntYear}/{cntMonth}")
    public ResponseEntity<ResponseCategorySumListDTO> categorySumList(@PathVariable int cntYear, @PathVariable int cntMonth) {
        ResponseCategorySumListDTO responseCategorySumListDTO = consumeService.findCategorySum(cntYear, cntMonth);
        return ResponseEntity.ok(responseCategorySumListDTO);
    }

    @GetMapping("/category/dailysum/{cntYear}/{cntMonth}")
    public ResponseEntity<MonthConsumeDTO> monthConsume(@PathVariable int cntYear, @PathVariable int cntMonth){
        System.out.println("month!!!!!!!!!!!");
        MonthConsumeDTO monthConsumeDTO = consumeService.findMonthConsume(cntYear, cntMonth);
        return ResponseEntity.ok(monthConsumeDTO);
    }


}
