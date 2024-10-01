package com.idle.kb_i_dle_backend.outcome.service;

import com.idle.kb_i_dle_backend.outcome.dto.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OutcomeService {

    ResponseCategorySumListDTO findCategorySum(Integer year, Integer month);

    MonthOutcomeDTO findMonthOutcome(Integer year, Integer month);

    CompareAverageCategoryOutcomeDTO compareWithAverage(int uid, Date start, Date end, String category);

    PossibleSaveOutcomeInMonthDTO findPossibleSaveOutcome(Integer uid, Date start, Date end);
}