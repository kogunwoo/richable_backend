package com.idle.kb_i_dle_backend.consume.repository;

import com.idle.kb_i_dle_backend.consume.dto.AvgCategorySumDTO;
import com.idle.kb_i_dle_backend.consume.entity.OutcomeAverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvgCategorySumRepository extends JpaRepository<OutcomeAverage, Integer> {

    @Query("SELECT new com.idle.kb_i_dle_backend.consume.dto.AvgCategorySumDTO(o.category, SUM(o.outcome)) " +
            "FROM OutcomeAverage o " +
            "WHERE o.ageGroup = '전체 평균' AND o.quater = :quater " +
            "GROUP BY o.category")
    List<AvgCategorySumDTO> findCategorySumByQuater(@Param("quater") String quater);  // @Param 사용
}
