package com.idle.kb_i_dle_backend.domain.invest.service;

import com.idle.kb_i_dle_backend.domain.invest.dto.*;

import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import java.util.List;
import java.util.Optional;

public interface InvestService {
    List<InvestDTO> getInvestList(int uid) throws  Exception;

    AvailableCashDTO getAvailableCash(int uid) throws Exception;

    List<CategorySumDTO> getInvestmentTendency(int uid) throws Exception;

    long totalAsset(int uid) throws Exception;

    MaxPercentageCategoryDTO getMaxPercentageCategory(int uid) throws Exception;

    List<RecommendedProductDTO> getRecommendedProducts(int uid) throws Exception;

    List<HighReturnProductDTO> getHighReturnStock(int uid) throws Exception;

    List<HighReturnProductDTO> getHighReturnCoin(int uid) throws Exception;

    HighReturnProductsDTO getHighReturnProducts(int uid) throws Exception;
}
