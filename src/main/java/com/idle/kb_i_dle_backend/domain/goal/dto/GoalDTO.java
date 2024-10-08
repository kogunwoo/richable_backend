package com.idle.kb_i_dle_backend.domain.goal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GoalDTO {
    private String category;
    private String title;
    private long amount;
    private int priority;
}
