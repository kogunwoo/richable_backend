package com.idle.kb_i_dle_backend.domain.goal.controller;

import com.idle.kb_i_dle_backend.common.dto.ResponseDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.*;
import com.idle.kb_i_dle_backend.domain.goal.service.GoalService;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goal")
@RequiredArgsConstructor
public class GoalController {

    private static final Logger log = LoggerFactory.getLogger(GoalController.class);
    private final GoalService goalService;

    @GetMapping("/")
    public ResponseEntity<ResponseDTO> getAssetGoal(){
        try {
            AssetGoalDTO assetGoalDTO = goalService.getAssetGoal(1);
            return ResponseEntity.ok(new ResponseDTO(false, assetGoalDTO));
        }catch (Exception e){
            return ResponseEntity.ok(new ResponseDTO(false,e.getMessage()));
        }
    }

    @GetMapping("/outcome")
    public ResponseEntity<ResponseDTO> getOutcomeGoal() {
        try {
            List<OutcomeGoalDTO> outcomeGoalDTOS = goalService.getOutcomeGoals(1);
            return ResponseEntity.ok(new ResponseDTO(true, outcomeGoalDTOS));
        }catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO(false,e.getMessage()));
        }
    }

    @PostMapping("/set")
    public ResponseEntity<ResponseDTO> setGoal(@RequestBody AddGoalDTO goal){
        try{
            GoalDTO goalDTO = goalService.saveGoal(1,goal);
            return ResponseEntity.ok(new ResponseDTO(true, goalDTO));
        }catch (Exception e){
            return ResponseEntity.ok(new ResponseDTO(false, e.getMessage()));
        }
    }

    @PutMapping("/set")
    public ResponseEntity<ResponseDTO> updateGoalAchive(@RequestBody RequestIndexDTO index){
        try{
            ResponseUpdateAchiveDTO responseUpdateAchiveDTO = goalService.updateAchive(1, index);
            return ResponseEntity.ok(new ResponseDTO(true, responseUpdateAchiveDTO));
        }catch (Exception e){
            return ResponseEntity.ok(new ResponseDTO(false, e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteGoal(@RequestBody RequestDeleteDTO requestDeleteDTO){
        try{
            ResponseIndexDTO responseIndexDTO = goalService.removeGoal(1, requestDeleteDTO);
            return ResponseEntity.ok(new ResponseDTO(true, responseIndexDTO));
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.ok(new ResponseDTO(false, e.getMessage()));
        }
    }

    @PutMapping("/priority")
    public ResponseEntity<ResponseDTO> updatePriority(@RequestBody RequestPriorityDTO requestPriorityDTO){
        try{
            ResponseUpdateAchiveDTO responseUpdateAchiveDTO = goalService.updatePriority(1, requestPriorityDTO);
            return ResponseEntity.ok(new ResponseDTO(true, responseUpdateAchiveDTO));
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.ok(new ResponseDTO(false, e.getMessage()));
        }
    }
}
