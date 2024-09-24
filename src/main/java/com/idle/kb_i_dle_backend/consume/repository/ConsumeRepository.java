package com.idle.kb_i_dle_backend.consume.repository;

import com.idle.kb_i_dle_backend.consume.entity.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ConsumeRepository extends JpaRepository<Outcome, Integer> {

    // Query to find by outcome expenditure category
    List<Outcome> findByOutcomeExp(String outcomeExp);

    // Query to find by household head age group from the outcome_average table fields
    List<Outcome> findByHouseholdHeadAgeGroup(String householdHeadAgeGroup);

    // Query to find by both household head age group and outcome expenditure category
    List<Outcome> findByHouseholdHeadAgeGroupAndOutcomeExp(String householdHeadAgeGroup, String outcomeExp);

    // Query to find by household size (from outcome_average)
    List<Outcome> findByHouseholdSizeGreaterThan(BigDecimal householdSize);
}

