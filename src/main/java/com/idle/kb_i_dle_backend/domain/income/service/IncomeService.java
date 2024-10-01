package com.idle.kb_i_dle_backend.domain.income.service;

import com.idle.kb_i_dle_backend.domain.income.dto.IncomeDTO;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface IncomeService {
    List<IncomeDTO> getIncomeList() throws Exception;

    long getIncomeSumInMonth(int uid , int year, int month) throws Exception;

    IncomeDTO getIncomeByIndex(Integer index) throws Exception;

    IncomeDTO addIncome(IncomeDTO incomeDTO) throws ParseException;

    IncomeDTO updateIncome(IncomeDTO incomeDTO) throws ParseException;

    Integer deleteIncomeByUidAndIndex(Integer index);

}
