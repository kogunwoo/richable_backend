package com.idle.kb_i_dle_backend.domain.outcome.service;

import com.idle.kb_i_dle_backend.domain.outcome.dto.*;

import java.util.Date;

public interface OutcomeService {

    ResponseCategorySumListDTO findCategorySum(Integer year, Integer month);

    MonthOutcomeDTO findMonthOutcome(Integer year, Integer month);

    CompareAverageCategoryOutcomeDTO compareWithAverage(int uid, int year, int month, String category);

    PossibleSaveOutcomeInMonthDTO findPossibleSaveOutcome(Integer uid, int year, int month);

    Simulation6MonthDTO calculate6MonthSaveOutcome(Integer uid, int year, int month) throws  Exception;
}