package com.idle.kb_i_dle_backend.consume.repository;

import com.idle.kb_i_dle_backend.consume.dto.AvgCategorySumDTO;
import com.idle.kb_i_dle_backend.consume.entity.OutcomeAverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryComRepository extends JpaRepository<OutcomeAverage, Integer> {

    List<OutcomeAverage> findByAgeGroupAndCategoryAndQuater(String ageGroup, String category, String quater);

    @Query("SELECT new com.idle.kb_i_dle_backend.consume.dto.CategorySumDTO(o.category, SUM(o.amount)) " +
            "FROM OutcomeUser o " +
            "WHERE o.category = :category " +
            "AND o.date = :date ")
    List<AvgCategorySumDTO> findCategorySumBydate(@Param("date") String date);
}