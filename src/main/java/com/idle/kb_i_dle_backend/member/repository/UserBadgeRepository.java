package com.idle.kb_i_dle_backend.member.repository;

import com.idle.kb_i_dle_backend.member.entity.UserBadgeEntity;
import com.idle.kb_i_dle_backend.member.entity.UserBadgeId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface UserBadgeRepository extends JpaRepository<UserBadgeEntity, UserBadgeId> {
    List<UserBadgeEntity> findByIdUid(int uid);
    List<UserBadgeEntity> findById_UidAndBadgeAchive(int uid, boolean badgeAchive);
}
