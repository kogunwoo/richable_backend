package com.idle.kb_i_dle_backend.finance.service;

import com.idle.kb_i_dle_backend.finance.dto.PriceSumDTO;
import com.idle.kb_i_dle_backend.finance.dto.SpotDTO;
import com.idle.kb_i_dle_backend.finance.entity.Spot;

import java.text.ParseException;
import java.util.List;

public interface SpotService {
    PriceSumDTO getTotalPriceByCategory(String category) throws Exception;

    PriceSumDTO getTotalPrice() throws Exception;

    List<SpotDTO> getSpotList() throws Exception;

    SpotDTO addSpot(SpotDTO spotDTO) throws ParseException;

    SpotDTO updateSpot(SpotDTO spotDTO) throws ParseException;

    Integer deleteSpotByUidAndIndex(Integer index);

}
