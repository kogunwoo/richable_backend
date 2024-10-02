package com.idle.kb_i_dle_backend.domain.goal.repository;

import com.idle.kb_i_dle_backend.domain.goal.entity.Goal;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Integer> {
    Goal findByIndex(int index);
    Goal save(Goal goal);
    Integer countByUidAndCategoryAndIsAchive(Member uid, String category, boolean isAchive);
    List<Goal> findByUidAndCategoryAndIsAchive(Member uid, String category, boolean isAchive);
    boolean existsByUidAndIndex(Member uid, int index);
    Integer deleteByUidAndIndex(Member uid, int index);
}
