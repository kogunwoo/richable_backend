package com.idle.kb_i_dle_backend.domain.goal.controller;

import com.idle.kb_i_dle_backend.common.dto.ResponseDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.AddGoalDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.GoalDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.RequestIndexDTO;
import com.idle.kb_i_dle_backend.domain.goal.dto.ResponseUpdateAchiveDTO;
import com.idle.kb_i_dle_backend.domain.goal.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goal")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

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
}
