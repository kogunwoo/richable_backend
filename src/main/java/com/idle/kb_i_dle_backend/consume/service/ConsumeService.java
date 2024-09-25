package com.idle.kb_i_dle_backend.consume.service;

import com.idle.kb_i_dle_backend.consume.dto.OutcomeAverageDTO;
import com.idle.kb_i_dle_backend.consume.dto.OutcomeUserDTO;

import java.util.List;

public interface ConsumeService {


    List<OutcomeAverageDTO> getAll();
    List<OutcomeUserDTO> getAllUser();
}