package com.idle.kb_i_dle_backend.consume.service;

import com.idle.kb_i_dle_backend.consume.dto.*;

import java.util.List;

public interface ConsumeService {


    List<OutcomeAverageDTO> getAll();
    List<OutcomeUserDTO> getAllUser();
    List<CategorySumDTO> getCategorySum(int uid, int year, int month);

    ResponseCategorySumListDTO findCategorySum(Integer year, Integer month);

    MonthConsumeDTO findMonthConsume(Integer year, Integer month);

    List<AvgCategorySumDTO> findCompareWithAvg(Integer uid, String category,Integer year, Integer month);

//    CategoryComDTO findCategoryCom(Integer year, Integer month);
}