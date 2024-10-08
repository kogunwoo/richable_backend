package com.idle.kb_i_dle_backend.domain.goal.controller;

import com.idle.kb_i_dle_backend.domain.goal.dto.AddGoalDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.AssetGoalDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.GoalDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.OutcomeGoalDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.RequestDeleteDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.RequestIndexDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.RequestPriorityDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.ResponseIndexDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.ResponseUpdateAchiveDTO;
import com.idle.kb_i_dle_backend.domain.goal.service.GoalService;
import com.idle.kb_i_dle_backend.domain.member.service.MemberService;
import com.idle.kb_i_dle_backend.global.dto.SuccessResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goal")
@RequiredArgsConstructor
public class GoalController {

    private static final Logger log = LoggerFactory.getLogger(GoalController.class);
    private final GoalService goalService;
    private final MemberService memberService;

    @GetMapping("")
    public ResponseEntity<SuccessResponseDTO> getAssetGoal() {
        Integer uid = memberService.getCurrentUid();
        log.info("Uid is " + uid);
        AssetGoalDTO assetGoalDTO = goalService.getAssetGoal(uid);
        return ResponseEntity.ok(new SuccessResponseDTO(false, assetGoalDTO));
    }

    @GetMapping("/outcome")
    public ResponseEntity<SuccessResponseDTO> getOutcomeGoal() {
        Integer uid = memberService.getCurrentUid();
        List<OutcomeGoalDTO> outcomeGoalDTOS = goalService.getOutcomeGoals(uid);
        return ResponseEntity.ok(new SuccessResponseDTO(true, outcomeGoalDTOS));
    }

    @PostMapping("/set")
    public ResponseEntity<SuccessResponseDTO> setGoal(@RequestBody AddGoalDTO goal) {
        Integer uid = memberService.getCurrentUid();
        GoalDTO goalDTO = goalService.saveGoal(uid, goal);
        return ResponseEntity.ok(new SuccessResponseDTO(true, goalDTO));
    }

    @PutMapping("/set")
    public ResponseEntity<SuccessResponseDTO> updateGoalAchive(@RequestBody RequestIndexDTO index) {
        Integer uid = memberService.getCurrentUid();
        ResponseUpdateAchiveDTO responseUpdateAchiveDTO = goalService.updateAchive(uid, index);
        return ResponseEntity.ok(new SuccessResponseDTO(true, responseUpdateAchiveDTO));

    }

    @DeleteMapping("/delete")
    public ResponseEntity<SuccessResponseDTO> deleteGoal(@RequestBody RequestDeleteDTO requestDeleteDTO) {
        Integer uid = memberService.getCurrentUid();
        ResponseIndexDTO responseIndexDTO = goalService.removeGoal(uid, requestDeleteDTO);
        return ResponseEntity.ok(new SuccessResponseDTO(true, responseIndexDTO));
    }

    @PutMapping("/priority")
    public ResponseEntity<SuccessResponseDTO> updatePriority(@RequestBody RequestPriorityDTO requestPriorityDTO) {
        Integer uid = memberService.getCurrentUid();
        ResponseUpdateAchiveDTO responseUpdateAchiveDTO = goalService.updatePriority(uid, requestPriorityDTO);
        return ResponseEntity.ok(new SuccessResponseDTO(true, responseUpdateAchiveDTO));
    }
}
