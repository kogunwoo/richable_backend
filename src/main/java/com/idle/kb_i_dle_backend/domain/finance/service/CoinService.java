package com.idle.kb_i_dle_backend.domain.finance.service;

import com.idle.kb_i_dle_backend.domain.finance.dto.CoinDTO;

import java.text.ParseException;
import java.util.List;

public interface CoinService {
    List<CoinDTO> getCoinList() throws Exception;

    CoinDTO addCoin(CoinDTO coinDTO) throws ParseException;

    CoinDTO updateCoin(CoinDTO coinDTO) throws ParseException;

    CoinDTO deleteCoin(Integer index) throws ParseException;
}
