package com.idle.kb_i_dle_backend.domain.finance.repository;

import com.idle.kb_i_dle_backend.domain.finance.entity.AssetSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetSummaryRepository extends JpaRepository<AssetSummary, Long> {
    AssetSummary findByUid(Integer ageGroupUid);
}
