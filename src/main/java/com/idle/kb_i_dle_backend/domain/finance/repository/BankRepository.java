package com.idle.kb_i_dle_backend.domain.finance.repository;

import com.idle.kb_i_dle_backend.domain.finance.entity.UserBank;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<UserBank, Integer> {

    List<UserBank> findAllByUidAndDeleteDateIsNull(int uid);

    // 특정 날짜 이전의 모든 데이터를 가져오는 쿼리 메서드
    List<UserBank> findAllByUidAndAddDateBefore(int uid, Date endDate);

    @Query("SELECT ub FROM UserBank ub WHERE ub.uid = :uid AND ub.addDate < :date")
    List<UserBank> findInvestmentsByUidAndDate(@Param("uid") Integer uid, @Param("date") Date date);
}