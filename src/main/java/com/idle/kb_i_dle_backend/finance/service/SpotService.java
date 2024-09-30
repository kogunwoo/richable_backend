package com.idle.kb_i_dle_backend.finance.service;

import com.idle.kb_i_dle_backend.finance.dto.PriceSumDTO;
import com.idle.kb_i_dle_backend.finance.dto.SpotDTO;
import com.idle.kb_i_dle_backend.finance.entity.Spot;

import java.util.List;

public interface SpotService {
    PriceSumDTO getTotalPriceByCategory(String category) throws Exception;

    PriceSumDTO getTotalPrice() throws Exception;

    List<Spot> getSpotList(Integer uid);

    SpotDTO addSpot(Integer uid, Spot spot);

    Integer getLastSpotIndex();

    SpotDTO updateSpot(Integer uid, Spot spot);

    void deleteSpotByUidAndIndex(Integer uid, Integer index);

}
