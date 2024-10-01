package com.idle.kb_i_dle_backend.domain.goal.repository;

import com.idle.kb_i_dle_backend.domain.goal.entity.Goal;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Integer> {

    @Query("select g "
            + "from Goal g "
            + "where g.index = ? ")
    Goal findByIndex(int index);
    Goal save(Goal goal);
    Integer countByUidAndCategory(Member uid, String category);
}
