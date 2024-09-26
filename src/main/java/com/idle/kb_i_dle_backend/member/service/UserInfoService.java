package com.idle.kb_i_dle_backend.member.service;

import com.idle.kb_i_dle_backend.member.entity.UserInfoEntity;
import com.idle.kb_i_dle_backend.member.repository.UserInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    // 닉네임을 기준으로 유저 정보 가져오기
    public UserInfoEntity getUserInfoByNickname(String nickname) {
        return userInfoRepository.findByNickname(nickname);
    }

    // token 값을 통해 본인 여부 확인
    public boolean isOwnProfile(String token, UserInfoEntity userInfoEntity) {
        // 유저 정보에 저장된 token과 요청에서 받은 token을 비교
        return userInfoEntity.getApiStockToken().equals(token);
    }
}
