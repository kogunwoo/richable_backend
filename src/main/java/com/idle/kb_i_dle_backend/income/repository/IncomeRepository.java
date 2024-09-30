package com.idle.kb_i_dle_backend.income.repository;

import com.idle.kb_i_dle_backend.income.entity.Income;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends CrudRepository<Income, Integer> {
    Income findByIndex(int id);

    List<Income> findByUidAndDateBetween(int uid, Date startDate, Date endDate);
}
