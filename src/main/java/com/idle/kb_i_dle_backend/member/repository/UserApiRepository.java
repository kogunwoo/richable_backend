package com.idle.kb_i_dle_backend.member.repository;

import com.idle.kb_i_dle_backend.member.entity.UserApiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserApiRepository extends JpaRepository<UserApiEntity, Integer> {
    // uid로 API 정보 조회
    UserApiEntity findByUid(int uid);
}
