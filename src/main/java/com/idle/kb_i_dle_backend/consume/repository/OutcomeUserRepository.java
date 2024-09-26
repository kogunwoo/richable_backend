package com.idle.kb_i_dle_backend.consume.repository;

import com.idle.kb_i_dle_backend.consume.entity.OutcomeAverage;
import com.idle.kb_i_dle_backend.consume.entity.OutcomeUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutcomeUserRepository extends JpaRepository<OutcomeUser, Integer> {
    List<OutcomeUser> findAll();

}
