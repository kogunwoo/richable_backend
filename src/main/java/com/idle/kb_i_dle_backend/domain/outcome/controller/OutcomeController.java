package com.idle.kb_i_dle_backend.domain.outcome.controller;

import com.idle.kb_i_dle_backend.domain.outcome.dto.CompareAverageCategoryOutcomeDTO;
import com.idle.kb_i_dle_backend.domain.outcome.dto.MonthOutcomeDTO;
import com.idle.kb_i_dle_backend.domain.outcome.dto.OutcomeUserDTO;
import com.idle.kb_i_dle_backend.domain.outcome.dto.PossibleSaveOutcomeInMonthDTO;
import com.idle.kb_i_dle_backend.domain.outcome.dto.ResponseCategorySumListDTO;
import com.idle.kb_i_dle_backend.domain.outcome.dto.Simulation6MonthDTO;
import com.idle.kb_i_dle_backend.domain.outcome.service.OutcomeService;
import com.idle.kb_i_dle_backend.global.dto.ErrorResponseDTO;
import com.idle.kb_i_dle_backend.global.dto.SuccessResponseDTO;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
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
@RequestMapping("/outcome")
@Slf4j
@RequiredArgsConstructor
public class OutcomeController {

    private final OutcomeService outcomeService;


    @GetMapping("/simulation/{cntYear}/{cntMonth}")
    public ResponseEntity<SuccessResponseDTO> simulation6Month(@PathVariable int cntYear, @PathVariable int cntMonth)
            throws ParseException {
        Simulation6MonthDTO simulation6MonthDTO = outcomeService.calculate6MonthSaveOutcome(100, cntYear, cntMonth);
        return ResponseEntity.ok(new SuccessResponseDTO(true, simulation6MonthDTO));
    }


    /**
     * 해당년도 해당 달에 줄일 수 있었던 비용 api
     *
     * @param cntYear
     * @param cntMonth
     * @return
     */
    @GetMapping("/review/sum/{cntYear}/{cntMonth}")
    public ResponseEntity<SuccessResponseDTO> saveOutcomeInMonth(@PathVariable int cntYear,
                                                                 @PathVariable int cntMonth) {
        Calendar calendar = Calendar.getInstance();
        PossibleSaveOutcomeInMonthDTO possibleSaveOutcomeInMonthDTO = outcomeService.findPossibleSaveOutcome(1, cntYear,
                cntMonth);
        SuccessResponseDTO successResponseDTO = new SuccessResponseDTO(true, possibleSaveOutcomeInMonthDTO);
        return ResponseEntity.ok(successResponseDTO);
    }


    /**
     * 평균 대비 카테고리별 소비량
     *
     * @param cntYear  년도
     * @param cntMonth 달
     * @return
     */
    @GetMapping("/compare/{cntYear}/{cntMonth}/{category}")
    public ResponseEntity<SuccessResponseDTO> compareWithAverage(@PathVariable int cntYear, @PathVariable int cntMonth,
                                                                 @PathVariable String category) {
        Calendar calendar = Calendar.getInstance();

        // 시작일 설정 (해당 연도와 월의 첫 번째 날)
        calendar.set(Calendar.YEAR, cntYear);
        calendar.set(Calendar.MONTH, cntMonth - 1); // Calendar.MONTH는 0-based이므로 1을 빼줍니다.
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();

        // 마지막 날 설정 (해당 연도와 월의 마지막 날)
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();

        CompareAverageCategoryOutcomeDTO compareAverageCategoryOutcomeDTO = outcomeService.compareWithAverage(1,
                cntYear, cntMonth, category);
        return ResponseEntity.ok(new SuccessResponseDTO(true, compareAverageCategoryOutcomeDTO));
    }


    @GetMapping("/category/sum/{cntYear}/{cntMonth}")
    public ResponseEntity<SuccessResponseDTO> categorySumList(@PathVariable int cntYear, @PathVariable int cntMonth) {
        ResponseCategorySumListDTO responseCategorySumListDTO = outcomeService.findCategorySum(cntYear, cntMonth);
        return ResponseEntity.ok(new SuccessResponseDTO(true, responseCategorySumListDTO));
    }

    @GetMapping("/category/dailysum/{cntYear}/{cntMonth}")
    public ResponseEntity<SuccessResponseDTO> monthConsume(@PathVariable int cntYear, @PathVariable int cntMonth) {
        System.out.println("month!!!!!!!!!!!");
        MonthOutcomeDTO monthOutcomeDTO = outcomeService.findMonthOutcome(cntYear, cntMonth);
        return ResponseEntity.ok(new SuccessResponseDTO(true, monthOutcomeDTO));
    }

    // 소비 CRUD

    // 소비 항목 리스트 반환
    @GetMapping("/all")
    public ResponseEntity<?> getTotalOutcomeList() {
        try {
            SuccessResponseDTO response = new SuccessResponseDTO(true, outcomeService.getOutcomeList());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 소비 항목 리스트 반환
    @GetMapping("/detail/{index}")
    public ResponseEntity<?> getOutcomeDetail(@PathVariable("index") Integer index) {
        try {
            SuccessResponseDTO response = new SuccessResponseDTO(true, outcomeService.getOutcomeByIndex(index));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 새로운 소득 추가
    @PostMapping("/add")
    public ResponseEntity<?> addOutcome(@RequestBody OutcomeUserDTO outcomeUserDTO) {
        try {
            SuccessResponseDTO response = new SuccessResponseDTO(true, outcomeService.addOutcome(outcomeUserDTO));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 소득 수정
    @PutMapping("/update")
    public ResponseEntity<?> updateOutcome(@RequestBody OutcomeUserDTO outcomeUserDTO) {
        try {
            SuccessResponseDTO response = new SuccessResponseDTO(true, outcomeService.updateOutcome(outcomeUserDTO));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 소득 삭제
    @DeleteMapping("/delete/{index}")
    public ResponseEntity<?> deleteOutcome(@PathVariable("index") Integer index) {
        try {
            Map<String, Object> indexData = new HashMap<>();
            indexData.put("index", outcomeService.deleteOutcomeByUidAndIndex(index));
            SuccessResponseDTO response = new SuccessResponseDTO(true, indexData);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


}
