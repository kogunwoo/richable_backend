package com.idle.kb_i_dle_backend.outcome.service;

import com.idle.kb_i_dle_backend.outcome.dto.*;

import java.util.List;

public interface OutcomeService {


    List<OutcomeAverageDTO> getAll();
    List<OutcomeUserDTO> getAllUser();
    List<CategorySumDTO> getCategorySum(int uid, int year, int month);

    ResponseCategorySumListDTO findCategorySum(Integer year, Integer month);

    MonthOutcomeDTO findMonthOutcome(Integer year, Integer month);
}