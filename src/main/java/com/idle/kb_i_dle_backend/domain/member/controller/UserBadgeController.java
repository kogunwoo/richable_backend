package com.idle.kb_i_dle_backend.domain.member.controller;


import com.idle.kb_i_dle_backend.domain.member.dto.UserBadgeDTO;
import com.idle.kb_i_dle_backend.domain.member.service.UserBadgeService;
import com.idle.kb_i_dle_backend.global.dto.SuccessResponseDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class UserBadgeController {

    private final UserBadgeService userBadgeService;


    // 목표 달성 뱃지 조회
    @GetMapping("/badge/having/{nickname}")
    public SuccessResponseDTO getAchievedBadges(@PathVariable String nickname) {
        List<UserBadgeD> badges = userBadgeService.getUserBadges(nickname, true);

        // response 감싸기
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", badges);

        return new SuccessResponseDTO("true", responseData);
    }

    // 목표 미달성 뱃지 조회
    @GetMapping("/badge/nothaving/{nickname}")
    public SuccessResponseDTO getNotAchievedBadges(@PathVariable String nickname) {
        List<UserBadgeDTO> badges = userBadgeService.getUserBadges(nickname, false);

        // response 감싸기
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", badges);

        return new SuccessResponseDTO("true", responseData);

    }

    // 전체 뱃지 조회
    @GetMapping("/badge/all/{nickname}")
    public SuccessResponseDTO getAllBadges(@PathVariable String nickname) {
        List<UserBadgeDTO> badges = userBadgeService.getAllBadges(nickname);

        // response 감싸기
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", badges);

        return new SuccessResponseDTO("true", responseData);
    }
}
