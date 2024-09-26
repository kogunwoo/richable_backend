package com.idle.kb_i_dle_backend.member.controller;

import com.idle.kb_i_dle_backend.common.dto.SuccessResponseDTO;
import com.idle.kb_i_dle_backend.member.entity.UserInfoEntity;
import com.idle.kb_i_dle_backend.member.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    // 유저 정보 가져오기 (자기 자신 / 남들)
    @GetMapping("/info/{nickname}")
    public SuccessResponseDTO getUserInfo(@PathVariable String nickname, @RequestParam("token") String token) {
        try {
            // 데이터베이스에서 유저 정보를 조회
            UserInfoEntity userInfoEntity = userInfoService.getUserInfoByNickname(nickname);
            if (userInfoEntity == null) {
                return new SuccessResponseDTO("false", null); // 유저가 없을 경우 실패 응답
            }

            // 자기 자신인지 확인하기 위한 token 값 비교
            boolean isOwnProfile = userInfoService.isOwnProfile(token, userInfoEntity);

            // 자기 자신의 프로필일 경우 모든 정보를 반환
            if (isOwnProfile) {
                Map<String, Object> userData = new HashMap<>();
                userData.put("nickname", userInfoEntity.getNickname());
                userData.put("email", userInfoEntity.getEmail());
                userData.put("img", userInfoEntity.getProfile());
                userData.put("birthYear", userInfoEntity.getBirthYear());
                userData.put("gender", userInfoEntity.getGender());
                userData.put("certification", userInfoEntity.isCertification());

                // API 정보 추가
                Map<String, Object> apiData = new HashMap<>();
                apiData.put("bank", userInfoEntity.getApiBank());

                Map<String, String> stockData = new HashMap<>();
                stockData.put("base", userInfoEntity.getApiStockBase());
                stockData.put("token", userInfoEntity.getApiStockToken());
                stockData.put("secret", userInfoEntity.getApiStockSecret());
                stockData.put("app", userInfoEntity.getApiStockApp());
                apiData.put("stock", stockData);

                Map<String, String> coinData = new HashMap<>();
                coinData.put("base", userInfoEntity.getApiCoinBase());
                coinData.put("secret", userInfoEntity.getApiCoinSecret());
                coinData.put("app", userInfoEntity.getApiCoinApp());
                apiData.put("coin", coinData);

                userData.put("api", apiData);

                // 성공 응답
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("data", userData);

                return new SuccessResponseDTO("true", responseData);
            } else {
                // 남들의 프로필일 경우 제한된 정보만 반환
                Map<String, Object> userData = new HashMap<>();
                userData.put("nickname", userInfoEntity.getNickname());
                userData.put("img", userInfoEntity.getProfile());

                // 성공 응답 (제한된 정보)
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("data", userData);

                return new SuccessResponseDTO("true", responseData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SuccessResponseDTO("false", null);
        }
    }
}
