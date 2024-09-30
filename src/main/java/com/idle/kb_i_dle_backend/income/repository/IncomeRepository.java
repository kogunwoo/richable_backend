package com.idle.kb_i_dle_backend.income.repository;

import com.idle.kb_i_dle_backend.income.entity.Income;
import org.springframework.data.repository.CrudRepository;

public interface IncomeRepository extends CrudRepository<Income, Integer> {
    Income findByIndex(int id);
}
