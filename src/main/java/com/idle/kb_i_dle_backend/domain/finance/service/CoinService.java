package com.idle.kb_i_dle_backend.domain.finance.service;

import com.idle.kb_i_dle_backend.domain.finance.dto.CoinDTO;
import java.text.ParseException;
import java.util.List;

public interface CoinService {
    List<CoinDTO> getCoinList(Integer uid) throws Exception;

    CoinDTO addCoin(Integer uid, CoinDTO coinDTO) throws ParseException;

    CoinDTO updateCoin(Integer uid, CoinDTO coinDTO) throws ParseException;

    CoinDTO deleteCoin(Integer uid, Integer index) throws ParseException;
}
