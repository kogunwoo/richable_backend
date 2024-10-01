package com.idle.kb_i_dle_backend.domain.outcome.service;

import com.idle.kb_i_dle_backend.domain.outcome.dto.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface OutcomeService {

    ResponseCategorySumListDTO findCategorySum(Integer year, Integer month);

    MonthOutcomeDTO findMonthOutcome(Integer year, Integer month);

    CompareAverageCategoryOutcomeDTO compareWithAverage(int uid, int year, int month, String category);

    PossibleSaveOutcomeInMonthDTO findPossibleSaveOutcome(Integer uid, int year, int month);

    Simulation6MonthDTO calculate6MonthSaveOutcome(Integer uid, int year, int month) throws  Exception;

    // 소비 CRUD 추가
    List<OutcomeUserDTO> getOutcomeList() throws Exception;

    OutcomeUserDTO getOutcomeByIndex(Integer index) throws Exception;

    OutcomeUserDTO addOutcome(OutcomeUserDTO outcomeUserDTO) throws ParseException;

    OutcomeUserDTO updateOutcome(OutcomeUserDTO outcomeUserDTO) throws ParseException;

    Integer deleteOutcomeByUidAndIndex(Integer index);

}