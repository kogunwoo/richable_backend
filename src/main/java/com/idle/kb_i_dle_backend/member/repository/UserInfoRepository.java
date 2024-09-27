package com.idle.kb_i_dle_backend.member.repository;

import com.idle.kb_i_dle_backend.member.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {
    // 닉네임을 통해 유저 정보를 조회하는 메서드
    UserInfoEntity findByNickname(String nickname);
    UserInfoEntity findByUid(int birthyear);
}
