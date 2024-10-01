package com.idle.kb_i_dle_backend.domain.goal.dto;

import lombok.Getter;

@Getter
public class AddGoalDTO {
    private String category;
    private String title;
    private long amount;
}
