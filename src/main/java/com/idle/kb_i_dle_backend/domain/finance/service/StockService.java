package com.idle.kb_i_dle_backend.domain.finance.service;

import com.idle.kb_i_dle_backend.domain.finance.dto.StockDTO;

import java.text.ParseException;
import java.util.List;

public interface StockService {
    List<StockDTO> getStockList() throws Exception;

    StockDTO addStock(StockDTO stockDTO) throws ParseException;

    StockDTO updateStock(StockDTO stockDTO) throws ParseException;

    StockDTO deleteStock(Integer index) throws ParseException;
}
