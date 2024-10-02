package com.idle.kb_i_dle_backend.domain.finance.service;

import com.idle.kb_i_dle_backend.domain.finance.dto.BankDTO;

import java.text.ParseException;
import java.util.List;

public interface BankService {
    List<BankDTO> getBankList() throws Exception;

    BankDTO addBank(BankDTO bankDTO) throws ParseException;

    BankDTO updateBank(BankDTO bankDTO) throws ParseException;

    BankDTO deleteBank(Integer index) throws ParseException;
}
