package com.idle.kb_i_dle_backend.domain.finance.repository;

import com.idle.kb_i_dle_backend.domain.finance.entity.AssetSummary;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface AssetSummaryRepository extends JpaRepository<AssetSummary, Long> {
    AssetSummary findFirstByUidAndUpdateDateBeforeOrderByUpdateDateDesc(Member uid , Date updateDate);
}
