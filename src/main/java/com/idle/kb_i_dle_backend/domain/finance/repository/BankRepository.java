package com.idle.kb_i_dle_backend.domain.finance.repository;

import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import com.idle.kb_i_dle_backend.domain.finance.entity.Bank;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {

    List<Bank> findAllByUidAndDeleteDateIsNull(int uid);

    // 특정 날짜 이전의 모든 데이터를 가져오는 쿼리 메서드
    List<Bank> findAllByUidAndAddDateBefore(int uid, Date endDate);

    @Query("SELECT ub FROM Bank ub WHERE ub.uid = :uid AND ub.addDate < :date")
    List<Bank> findInvestmentsByUidAndDate(@Param("uid") Integer uid, @Param("date") Date date);

    // Bank 전체 조회
    List<Bank> findByUid(Member uid);

    // 새로운 메서드: 특정 사용자의 "입출금" 또는 "현금" 카테고리에 해당하는 은행 계좌 조회
    @Query("SELECT ub FROM Bank ub WHERE ub.uid = :uid AND ub.category IN ('입출금', '현금') AND ub.deleteDate IS NULL")
    List<Bank> findByUidAndSpecificCategoriesAndDeleteDateIsNull(@Param("uid") Member uid);

    // bank crud
    // 삭제되지 않은 금융 자산(Bank) 전체 조회
    List<Bank> findByUidAndDeleteDateIsNull(Member uid);

    // 특정 index값의 정보 조회
    Optional<Bank> findByIndexAndDeleteDateIsNull(@Param("index")Integer index);
}