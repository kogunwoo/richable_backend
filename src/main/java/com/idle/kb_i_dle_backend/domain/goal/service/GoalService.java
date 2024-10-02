package com.idle.kb_i_dle_backend.domain.goal.service;

import com.idle.kb_i_dle_backend.domain.goal.dto.*;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;

public interface GoalService {


    //목표 저장 메서드
    GoalDTO saveGoal(int uid ,AddGoalDTO addGoalDTO) throws Exception;

    //소비 목표 저장 메서드
    GoalDTO saveOutcomeGoal(Member member, AddGoalDTO addGoalDTO);
    
    //자산 목표 저장 메서드
    GoalDTO saveAssetGoal(Member member, AddGoalDTO addGoalDTO) throws Exception;

    //목표 달성 수정 메서드
    ResponseUpdateAchiveDTO updateAchive(int uid , RequestIndexDTO requestIndexDTO) throws Exception;

    //목표 삭제 메서드
    ResponseIndexDTO removeGoal(int uid , RequestIndexDTO requestIndexDTO) throws Exception;

    //우선 순위 변경 메서드
    ResponseUpdateAchiveDTO updatePriority(int uid, RequestPriorityDTO requestPriorityDTO) throws Exception;

}
