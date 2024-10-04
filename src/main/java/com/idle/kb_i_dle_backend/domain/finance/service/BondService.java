package com.idle.kb_i_dle_backend.domain.finance.service;

import com.idle.kb_i_dle_backend.domain.finance.dto.BondDTO;

import java.text.ParseException;
import java.util.List;

public interface BondService {
    List<BondDTO> getBondList() throws Exception;

    BondDTO addBond(BondDTO bondDTO) throws ParseException;

    BondDTO updateBond(BondDTO bondDTO) throws ParseException;

    BondDTO deleteBond(Integer index) throws ParseException;
}
