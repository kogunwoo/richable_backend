package com.idle.kb_i_dle_backend.domain.finance.service;

import com.idle.kb_i_dle_backend.domain.finance.dto.PriceSumDTO;
import com.idle.kb_i_dle_backend.domain.finance.dto.SpotDTO;
import java.text.ParseException;
import java.util.List;

public interface SpotService {
    PriceSumDTO getTotalPriceByCategory(Integer uid, String category) throws Exception;

    PriceSumDTO getTotalPrice(Integer uid) throws Exception;

    List<SpotDTO> getSpotList(Integer uid) throws Exception;

    SpotDTO addSpot(Integer uid, SpotDTO spotDTO) throws ParseException;

    SpotDTO updateSpot(Integer uid, SpotDTO spotDTO) throws ParseException;

    SpotDTO deleteSpot(Integer uid, Integer index);

}
