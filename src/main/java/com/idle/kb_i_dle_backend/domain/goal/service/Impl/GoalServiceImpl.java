package com.idle.kb_i_dle_backend.domain.goal.service.Impl;

import com.idle.kb_i_dle_backend.domain.goal.dto.AddGoalDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.GoalDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.RequestIndexDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.ResponseUpdateAchiveDTO;
import com.idle.kb_i_dle_backend.domain.goal.entity.Goal;
import com.idle.kb_i_dle_backend.domain.goal.repository.GoalRepository;
import com.idle.kb_i_dle_backend.domain.goal.service.GoalService;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import com.idle.kb_i_dle_backend.domain.member.service.MemberService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final MemberService memberService;

    /**
     * 목표 저장
     * @param uid
     * @param addGoalDTO
     * @return
     */
    @Override
    public GoalDTO saveGoal(int uid ,AddGoalDTO addGoalDTO) throws Exception {
        //맞는 유저인지
        Member member = memberService.findMemberByUid(uid).orElseThrow();

        //목표 카테고리 맞는지
        if(addGoalDTO.getCategory().equals("소비")){
            return saveOutcomeGoal(member,addGoalDTO);
        }else if(addGoalDTO.getCategory().equals("자산")){
            return saveAssetGoal(member, addGoalDTO);
        }else{
            throw new Exception("없는 카테고리 입니다.");
        }


    }

    public GoalDTO saveOutcomeGoal(Member member, AddGoalDTO addGoalDTO){
        // 사용자가 현재 가지고 있는 목표의 개수를 가져옴
        int currentGoalCount = goalRepository.countByUidAndCategory(member, addGoalDTO.getCategory());
        //목표 생성
        Goal goal = new Goal(member, addGoalDTO.getCategory(),addGoalDTO.getTitle(), addGoalDTO.getAmount(), currentGoalCount + 1);
        //목표 저장
        Goal responseGoal = goalRepository.save(goal);
        return new GoalDTO(responseGoal.getCategory(), responseGoal.getTitle(), responseGoal.getAmount(), responseGoal.getPriority());
    }

    public GoalDTO saveAssetGoal(Member member, AddGoalDTO addGoalDTO) throws Exception {
        // 사용자가 현재 가지고 있는 목표의 개수를 가져옴
        int currentGoalCount = goalRepository.countByUidAndCategory(member, addGoalDTO.getCategory());
        //목표 생성
        if(currentGoalCount == 0){
            Goal goal = new Goal(member, addGoalDTO.getCategory(),addGoalDTO.getTitle(), addGoalDTO.getAmount(), currentGoalCount + 1);
            //목표 저장
            Goal responseGoal = goalRepository.save(goal);
            return new GoalDTO(responseGoal.getCategory(), responseGoal.getTitle(), responseGoal.getAmount(), responseGoal.getPriority());
        }else{
            throw new Exception("이미 자산 목표가 있습니다.");
        }
    }

    @Override
    @Transactional
    public ResponseUpdateAchiveDTO updateAchive(int uid, RequestIndexDTO requestIndexDTO) throws Exception{
        Member memer = memberService.findMemberByUid(uid).orElseThrow();
        //해당인덱스의 goal을 가져오고
        Goal goal = goalRepository.getById(requestIndexDTO.getIndex());
        //achive와 priority를 수정
        goal.updateToAchive();
        //responseUpdateachive에 반환
        return new ResponseUpdateAchiveDTO(goal.getIndex(), goal.getIsAchive(), goal.getPriority());
    }


}
