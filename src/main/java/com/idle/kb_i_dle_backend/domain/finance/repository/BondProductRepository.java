package com.idle.kb_i_dle_backend.domain.finance.repository;

import com.idle.kb_i_dle_backend.domain.finance.entity.BondProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BondProductRepository extends JpaRepository<BondProduct,Integer> {
    List<BondProduct> findTop5ByOrderByPriceDesc();

}
