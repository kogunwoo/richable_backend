package com.idle.kb_i_dle_backend.domain.goal.service.Impl;

import com.idle.kb_i_dle_backend.domain.goal.dto.*;
import com.idle.kb_i_dle_backend.domain.goal.entity.Goal;
import com.idle.kb_i_dle_backend.domain.goal.repository.GoalRepository;
import com.idle.kb_i_dle_backend.domain.goal.service.GoalService;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import com.idle.kb_i_dle_backend.domain.member.service.MemberService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        int currentGoalCount = goalRepository.countByUidAndCategoryAndIsAchive(member, addGoalDTO.getCategory(), false);
        //목표 생성
        Goal goal = new Goal(member, addGoalDTO.getCategory(),addGoalDTO.getTitle(), addGoalDTO.getAmount(), currentGoalCount + 1);
        //목표 저장
        Goal responseGoal = goalRepository.save(goal);
        return new GoalDTO(responseGoal.getCategory(), responseGoal.getTitle(), responseGoal.getAmount(), responseGoal.getPriority());
    }

    public GoalDTO saveAssetGoal(Member member, AddGoalDTO addGoalDTO) throws Exception {
        // 사용자가 현재 가지고 있는 목표의 개수를 가져옴
        int currentGoalCount = goalRepository.countByUidAndCategoryAndIsAchive(member, addGoalDTO.getCategory(), false);
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
        Member member = memberService.findMemberByUid(uid).orElseThrow();

        if(goalRepository.existsByUidAndIndex(member, requestIndexDTO.getIndex())){
            //해당인덱스의 goal을 가져오고
            Goal goal = goalRepository.getById(requestIndexDTO.getIndex());
            //achive와 priority를 수정
            goal.updateToAchive();
            //responseUpdateachive에 반환
            return new ResponseUpdateAchiveDTO(goal.getIndex(), goal.getIsAchive(), goal.getPriority());
        }else{
            throw new Exception("없는 index입니다.");
        }
    }

    @Override
    @Transactional
    public ResponseIndexDTO removeGoal(int uid, RequestIndexDTO requestIndexDTO) throws Exception {
        Member member = memberService.findMemberByUid(uid).orElseThrow();
        //해당 goal이 있는지 확인
        if(goalRepository.existsByUidAndIndex(member, requestIndexDTO.getIndex())){
            int goal = goalRepository.deleteByUidAndIndex(member, requestIndexDTO.getIndex());
            return new ResponseIndexDTO(requestIndexDTO.getIndex());
        }else{
            throw new Exception("없는 index입니다.");
        }
    }

    @Override
    @Transactional
    public ResponseUpdateAchiveDTO updatePriority(int uid, RequestPriorityDTO requestPriorityDTO) throws Exception {
        Member member = memberService.findMemberByUid(uid).orElseThrow();

        if(goalRepository.existsByUidAndIndex(member, requestPriorityDTO.getIndex())){
            int currentGoalCount = goalRepository.countByUidAndCategoryAndIsAchive(member, "소비", false);
            List<Goal> currentGoals = goalRepository.findByUidAndCategoryAndIsAchive(member, "소비", false);
            if(requestPriorityDTO.getPriority() < 0 || requestPriorityDTO.getPriority() > currentGoalCount){
                throw new IllegalArgumentException("우선 순위 값을 확인해 주세요");
            }

            Goal targetGoal = goalRepository.getById(requestPriorityDTO.getIndex());
            int oldPriority = targetGoal.getPriority();

            targetGoal.updatePriority(requestPriorityDTO.getPriority());


            for(Goal goal : currentGoals){
                if(goal != targetGoal){
                    if(goal.getPriority() < oldPriority && goal.getPriority() >= requestPriorityDTO.getPriority()){
                        goal.updatePriority(goal.getPriority() + 1);
                    }else if(goal.getPriority() > oldPriority && goal.getPriority() <= requestPriorityDTO.getPriority()){
                        goal.updatePriority(goal.getPriority() -1 );
                    }
                }
            }

            goalRepository.saveAll(currentGoals);
            return new ResponseUpdateAchiveDTO(requestPriorityDTO.getIndex(), targetGoal.getIsAchive(), targetGoal.getPriority());
        }else{
            throw new Exception("없는 index입니다.");
        }
    }


}
