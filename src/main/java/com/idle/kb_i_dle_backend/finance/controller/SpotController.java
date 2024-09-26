package com.idle.kb_i_dle_backend.finance.controller;

import com.idle.kb_i_dle_backend.finance.service.SpotServiceImpl;
import com.idle.kb_i_dle_backend.member.util.JwtProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@RequestMapping("/finance")
@Slf4j
@RequiredArgsConstructor
public class SpotController {

    private final SpotServiceImpl spotServiceImpl;

    // 카테고리에 따른 총 가격 반환
    @GetMapping("/spot/sum")
    public ResponseEntity<Long> getTotalPriceByCategory(@RequestBody HashMap<String, String> map, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer uid = (Integer) session.getAttribute("uid");
        String category = map.get("category");
        Long totalPrice = spotServiceImpl.getTotalPriceByCategory(uid, category);
        return ResponseEntity.ok(totalPrice);
    }
}
