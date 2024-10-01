//package com.idle.kb_i_dle_backend.domain.member.controller;
//
//
//import com.idle.kb_i_dle_backend.common.dto.SuccessResponseDTO;
//import com.idle.kb_i_dle_backend.domain.member.dto.UserGradeDTO;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/member")
//public class UserGradeController {
//
//    @GetMapping("/grade/{nickname}")
//    public SuccessResponseDTO getUserGrade(@PathVariable String nickname) {
//        // 여기서 등급을 가져오는 로직을 구현 (DB나 서비스에서 조회)
//        UserGradeDTO userGradeDTO = new UserGradeDTO();
//        userGradeDTO.setId(nickname);
//        userGradeDTO.setGrade("리치");
//
//        // response를 감싸는 구조로 반환
//        Map<String, Object> responseData = new HashMap<>();
//        responseData.put("data", userGradeDTO);
//
//        return new SuccessResponseDTO("true", responseData);
//    }
//}
