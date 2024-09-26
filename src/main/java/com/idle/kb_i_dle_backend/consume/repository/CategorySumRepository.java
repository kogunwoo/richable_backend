package com.idle.kb_i_dle_backend.consume.repository;

import com.idle.kb_i_dle_backend.consume.dto.CategorySumDTO;
import com.idle.kb_i_dle_backend.consume.entity.OutcomeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategorySumRepository extends JpaRepository<OutcomeUser, Integer>{
    /**
     * 해당 년도 해당 달의 카테고리별로 소비합을 구함
     * @param uid 유저 id
     * @param year 년도
     * @param month 달
     * @return 카테고리별 소비합
     */
    @Query("SELECT new com.idle.kb_i_dle_backend.consume.dto.CategorySumDTO(o.category, SUM(o.amount)) " +
            "FROM OutcomeUser o WHERE o.uid = :uid " +
            "AND YEAR(o.date) = :year AND MONTH(o.date) = :month and where o.category = " +
            "GROUP BY o.category")
    List<CategorySumDTO> findCategorySumByUidAndYearAndMonth(@Param("uid") int uid, @Param("year") int year , @Param("month") int month);

    /**
     * 날짜순으로 해당 년도 해당 달의 소비들을 LIST로 받음
     * @param uid
     * @param year
     * @param month
     * @return
     */
    @Query("SELECT o.amount " +
            "FROM OutcomeUser o " +
            "WHERE o.uid = :uid AND YEAR(o.date) = :year AND MONTH(o.date) = :month " +
            "ORDER BY o.date")
    List<Long> findAmountAllByUidAndYearAndMonth(@Param("uid") int uid, @Param("year") int year , @Param("month") int month);
}
