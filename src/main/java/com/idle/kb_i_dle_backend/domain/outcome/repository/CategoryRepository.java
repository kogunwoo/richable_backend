package com.idle.kb_i_dle_backend.domain.outcome.repository;

import com.idle.kb_i_dle_backend.domain.outcome.entity.OutcomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<OutcomeCategory, Integer> {
    OutcomeCategory findByCategoryNameStartingWith(String categoryName);
}
