package com.idle.kb_i_dle_backend.domain.goal.service;

import com.idle.kb_i_dle_backend.domain.goal.dto.AddGoalDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.GoalDTO;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;

public interface GoalService {


    //목표 저장 메서드
    GoalDTO saveGoal(int uid ,AddGoalDTO addGoalDTO) throws Exception;

    //소비 목표 저장 메서드
    GoalDTO saveOutcomeGoal(Member member, AddGoalDTO addGoalDTO);
    
    //자산 목표 저장 메서드
    GoalDTO saveAssetGoal(Member member, AddGoalDTO addGoalDTO) throws Exception;
}
