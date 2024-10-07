package com.idle.kb_i_dle_backend.domain.income.repository;

import com.idle.kb_i_dle_backend.domain.income.entity.Income;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("SELECT I " +
            "FROM Income I " +
            "WHERE I.uid = :uid " +
            "and YEAR (I.date) = :year " +
            "and month (I.date) = :month")
    List<Income> findByUidAndYearAndMonth(@Param("uid") Member uid, @Param("year") int year, @Param("month") int month);

    @Query(value = "SELECT DATE_FORMAT(i.date, '%Y-%m') AS month, SUM(i.amount) AS total_income " +
            "FROM asset.income i " +
            "WHERE i.uid = :uid " +
            "GROUP BY DATE_FORMAT(i.date, '%Y-%m')", nativeQuery = true)
    List<Object[]> findMonthlyIncomeByUid(@Param("uid") int uid);

    // 소득 전체 조회
    List<Income> findByUid(Member uid);

    // 특정 index값의 소득 조회
    Optional<Income> findByIndex(@Param("index") Integer index);

    // 특정 index값의 소득 삭제
    void deleteByIndex(@Param("index") Integer index);
}
