package com.idle.kb_i_dle_backend.domain.invest.service;

import com.idle.kb_i_dle_backend.domain.invest.dto.AvailableCashDTO;
import com.idle.kb_i_dle_backend.domain.invest.dto.CategorySumDTO;
import com.idle.kb_i_dle_backend.domain.invest.dto.InvestDTO;
import com.idle.kb_i_dle_backend.domain.invest.dto.MaxPercentageCategoryDTO;
import java.util.List;

public interface InvestService {
    List<InvestDTO> getInvestList() throws  Exception;

    AvailableCashDTO getAvailableCash() throws Exception;

    List<CategorySumDTO> getInvestmentTendency() throws Exception;

    long totalAsset() throws Exception;

    MaxPercentageCategoryDTO getMaxPercentageCategory() throws Exception;
}
