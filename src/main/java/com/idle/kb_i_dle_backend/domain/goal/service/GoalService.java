package com.idle.kb_i_dle_backend.domain.goal.service;

import com.idle.kb_i_dle_backend.domain.goal.dto.AddGoalDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.AssetGoalDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.GoalDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.OutcomeGoalDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.RequestDeleteDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.RequestIndexDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.RequestPriorityDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.ResponseIndexDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.ResponseUpdateAchiveDTO;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import java.util.List;

public interface GoalService {


    //목표 저장 메서드
    GoalDTO saveGoal(int uid, AddGoalDTO addGoalDTO);

    //소비 목표 저장 메서드
    GoalDTO saveOutcomeGoal(Member member, AddGoalDTO addGoalDTO);

    //자산 목표 저장 메서드
    GoalDTO saveAssetGoal(Member member, AddGoalDTO addGoalDTO);

    //목표 달성 수정 메서드
    ResponseUpdateAchiveDTO updateAchive(int uid, RequestIndexDTO requestIndexDTO);

    //목표 삭제 메서드
    ResponseIndexDTO removeGoal(int uid, RequestDeleteDTO requestDeleteDTO);

    //우선 순위 변경 메서드
    ResponseUpdateAchiveDTO updatePriority(int uid, RequestPriorityDTO requestPriorityDTO);

    //자산 목표 조회

    //소비 목표 조회
    List<OutcomeGoalDTO> getOutcomeGoals(int uid);

    AssetGoalDTO getAssetGoal(int uid);
}
