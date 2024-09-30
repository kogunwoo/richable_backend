package com.idle.kb_i_dle_backend.outcome.repository;

import com.idle.kb_i_dle_backend.outcome.entity.OutcomeAverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AverageOutcomeRepository extends JpaRepository<OutcomeAverage, Integer> {

    List<OutcomeAverage> findAll();

}

