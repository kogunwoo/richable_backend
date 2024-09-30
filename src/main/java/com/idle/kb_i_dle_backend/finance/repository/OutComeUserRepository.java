package com.idle.kb_i_dle_backend.finance.repository;

import com.idle.kb_i_dle_backend.outcome.entity.OutcomeUser;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutComeUserRepository extends JpaRepository<OutcomeUser, Integer> {

    List<OutcomeUser> findAllByUid(int uid);

    List<OutcomeUser> findByUidAndDateBetween(int uid, Date startDate, Date endDate);
}
