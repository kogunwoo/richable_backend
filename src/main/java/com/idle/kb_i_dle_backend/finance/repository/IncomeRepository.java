package com.idle.kb_i_dle_backend.finance.repository;

import com.idle.kb_i_dle_backend.income.entity.Income;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<Income,Integer> {

    List<Income> findByUidAndDateBetween(int uid, Date startDate, Date endDate);

}
