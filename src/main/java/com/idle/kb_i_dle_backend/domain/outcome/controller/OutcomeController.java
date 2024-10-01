package com.idle.kb_i_dle_backend.domain.outcome.controller;

import com.idle.kb_i_dle_backend.common.dto.ResponseDTO;
import com.idle.kb_i_dle_backend.domain.outcome.dto.*;
import com.idle.kb_i_dle_backend.domain.outcome.service.OutcomeService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/outcome")
@Slf4j
@RequiredArgsConstructor
public class OutcomeController {

    private final OutcomeService outcomeService;


    @GetMapping("/simulation/{cntYear}/{cntMonth}")
    public ResponseEntity<ResponseDTO> simulation6Month(@PathVariable int cntYear, @PathVariable int cntMonth){

        try {
            Simulation6MonthDTO simulation6MonthDTO = outcomeService.calculate6MonthSaveOutcome(1, cntYear, cntMonth);
            return ResponseEntity.ok(new ResponseDTO(true, simulation6MonthDTO));
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(new ResponseDTO(false, null));
    }


    /**
     * 해당년도 해당 달에 줄일 수 있었던 비용 api
     * @param cntYear
     * @param cntMonth
     * @return
     */
    @GetMapping("/review/sum/{cntYear}/{cntMonth}")
    public ResponseEntity<ResponseDTO> saveOutcomeInMonth(@PathVariable int cntYear, @PathVariable int cntMonth){
        Calendar calendar = Calendar.getInstance();

        // 시작일 설정 (해당 연도와 월의 첫 번째 날)
        calendar.set(Calendar.YEAR, cntYear);
        calendar.set(Calendar.MONTH, cntMonth - 1); // Calendar.MONTH는 0-based이므로 1을 빼줍니다.
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();

        // 마지막 날 설정 (해당 연도와 월의 마지막 날)
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();

        PossibleSaveOutcomeInMonthDTO possibleSaveOutcomeInMonthDTO = outcomeService.findPossibleSaveOutcome(1, cntYear, cntMonth);
        ResponseDTO responseDTO = new ResponseDTO(true, possibleSaveOutcomeInMonthDTO);
        return ResponseEntity.ok(responseDTO);
    }


    /**
     * 평균 대비 카테고리별 소비량
     * @param cntYear 년도
     * @param cntMonth 달
     * @return
     */
    @GetMapping("/compare/{cntYear}/{cntMonth}/{category}")
    public ResponseEntity<CompareAverageCategoryOutcomeDTO> compareWithAverage(@PathVariable int cntYear,  @PathVariable int cntMonth,  @PathVariable String category) {
        Calendar calendar = Calendar.getInstance();

        // 시작일 설정 (해당 연도와 월의 첫 번째 날)
        calendar.set(Calendar.YEAR, cntYear);
        calendar.set(Calendar.MONTH, cntMonth - 1); // Calendar.MONTH는 0-based이므로 1을 빼줍니다.
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();

        // 마지막 날 설정 (해당 연도와 월의 마지막 날)
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();

        CompareAverageCategoryOutcomeDTO compareAverageCategoryOutcomeDTO = outcomeService.compareWithAverage(1,  cntYear, cntMonth , category);
        return ResponseEntity.ok(compareAverageCategoryOutcomeDTO);
    }


    @GetMapping("/category/sum/{cntYear}/{cntMonth}")
    public ResponseEntity<ResponseDTO> categorySumList(@PathVariable int cntYear, @PathVariable int cntMonth) {
        ResponseCategorySumListDTO responseCategorySumListDTO = outcomeService.findCategorySum(cntYear, cntMonth);
        return ResponseEntity.ok(new ResponseDTO(true ,responseCategorySumListDTO) );
    }

    @GetMapping("/category/dailysum/{cntYear}/{cntMonth}")
    public ResponseEntity<MonthOutcomeDTO> monthConsume(@PathVariable int cntYear, @PathVariable int cntMonth){
        System.out.println("month!!!!!!!!!!!");
        MonthOutcomeDTO monthOutcomeDTO = outcomeService.findMonthOutcome(cntYear, cntMonth);
        return ResponseEntity.ok(monthOutcomeDTO);
    }


}
