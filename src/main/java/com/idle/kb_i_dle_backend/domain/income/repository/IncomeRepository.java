package com.idle.kb_i_dle_backend.domain.income.repository;

import com.idle.kb_i_dle_backend.domain.income.entity.Income;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Repository
public interface IncomeRepository extends CrudRepository<Income, Integer> {

    List<Income> findByUidAndDateBetween(User uid, Date startDate, Date endDate);

    // 소득 전체 조회
    List<Income> findByUid(Member uid);

    // 특정 index값의 소득 조회
    Optional<Income> findByIndex(@Param("index")Integer index);

    // 특정 index값의 소득 삭제
    void deleteByIndex(@Param("index")Integer index);
}
