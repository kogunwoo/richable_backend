package com.idle.kb_i_dle_backend.domain.member.repository;

import com.idle.kb_i_dle_backend.domain.member.entity.MemberAPI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberApiRepository extends JpaRepository<MemberAPI, Integer> {
    // uid로 API 정보 조회
    MemberAPI findByUid(int uid);
}
