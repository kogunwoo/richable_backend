package com.idle.kb_i_dle_backend.finance.service;

import com.idle.kb_i_dle_backend.finance.dto.SpotDTO;
import com.idle.kb_i_dle_backend.finance.entity.UserSpot;

import java.util.List;

public interface SpotService {
    Long getTotalPriceByCategory(Integer uid, String category);

    Long getTotalPrice(Integer uid);

    List<UserSpot> getSpotList(Integer uid);

    SpotDTO addSpot(Integer uid, UserSpot userSpot);

    Integer getLastSpotIndex();

    SpotDTO updateSpot(Integer uid, UserSpot userSpot);

    void deleteSpotByUidAndIndex(Integer uid, Integer index);

}
