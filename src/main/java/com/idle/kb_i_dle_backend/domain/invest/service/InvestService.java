package com.idle.kb_i_dle_backend.domain.invest.service;

import com.idle.kb_i_dle_backend.domain.invest.dto.*;

import java.util.List;

public interface InvestService {
    List<InvestDTO> getInvestList() throws  Exception;

    AvailableCashDTO getAvailableCash() throws Exception;

    List<CategorySumDTO> getInvestmentTendency() throws Exception;

    long totalAsset() throws Exception;

    MaxPercentageCategoryDTO getMaxPercentageCategory() throws Exception;

    List<RecommendedProductDTO> getRecommendedProducts() throws Exception;
}
