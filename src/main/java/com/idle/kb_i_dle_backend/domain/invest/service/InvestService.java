package com.idle.kb_i_dle_backend.domain.invest.service;

import com.idle.kb_i_dle_backend.domain.invest.dto.InvestDTO;
import java.util.List;

public interface InvestService {
    List<InvestDTO> getInvestList() throws  Exception;

}
