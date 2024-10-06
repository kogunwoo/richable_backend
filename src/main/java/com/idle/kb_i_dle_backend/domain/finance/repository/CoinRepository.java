package com.idle.kb_i_dle_backend.domain.finance.repository;

import com.idle.kb_i_dle_backend.domain.finance.entity.Coin;
import com.idle.kb_i_dle_backend.domain.finance.entity.CoinProduct;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {
    List<Coin> findAllByUidAndDeleteDateIsNull(Optional<Member> uid);

    @Query(value = "SELECT a.balance, b.closing_price " +
            "FROM asset.coin a " +
            "JOIN product.coin_list b ON a.currency = b.coin_name " +
            "WHERE a.uid = :uid", nativeQuery = true)
    List<Object[]> findCoinBalanceAndPriceByUid(@Param("uid") Optional<Member> uid);


    @Query(value = "SELECT b.closing_price " +
            "FROM asset.coin a " +
            "JOIN product.coin_list b ON a.currency = b.coin_name " +
            "WHERE b.coin_name = :currency limit 1", nativeQuery = true)
    Double findCoinPriceBy(@Param("currency") String currency);


    @Query(value = "SELECT c.balance, " +
            "CASE " +
            "  WHEN :monthsAgo = 1 THEN clp.1m_b_price " +
            "  WHEN :monthsAgo = 2 THEN clp.2m_b_price " +
            "  WHEN :monthsAgo = 3 THEN clp.3m_b_price " +
            "  WHEN :monthsAgo = 4 THEN clp.4m_b_price " +
            "  WHEN :monthsAgo = 5 THEN clp.5m_b_price " +
            "  WHEN :monthsAgo = 6 THEN clp.6m_b_price " +
            "END AS closingPrice " +
            "FROM asset.coin c " +  // asset.coin 테이블에 대한 c 별칭 추가
            "JOIN product.coin_list cl ON c.currency = cl.coin_name " +
            "JOIN product.coin_list_price clp ON cl.coin_name = clp.coin_name " +
            "WHERE c.uid = :uid " +
            "AND c.add_date <= :endDate " +
            "AND c.delete_date IS NULL", nativeQuery = true)
    List<Object[]> findCoinBalanceAndPriceByUserIdAndBefore(@Param("uid") Member uid,
                                                            @Param("endDate") Date endDate,
                                                            @Param("monthsAgo") int monthsAgo);


    @Query(value = "SELECT " +
            "CASE :monthsAgo " +
            "  WHEN 1 THEN clp.1m_b_price " +
            "  WHEN 2 THEN clp.2m_b_price " +
            "  WHEN 3 THEN clp.3m_b_price " +
            "  WHEN 4 THEN clp.4m_b_price " +
            "  WHEN 5 THEN clp.5m_b_price " +
            "  WHEN 6 THEN clp.6m_b_price " +
            "END AS closingPrice " +
            "FROM product.coin_list cl " +
            "JOIN product.coin_list_price clp ON cl.coin_name = clp.coin_name " +
            "WHERE cl.coin_name = :coinName ", nativeQuery = true)
    Double getCoinPriceForMonth(@Param("coinName") String coinName, @Param("monthsAgo") int monthsAgo);

    List<Coin> findByUid(Optional<Member> uid);

    @Query("SELECT cl FROM CoinProduct cl ORDER BY CAST(cl.closingPrice AS double) DESC")
    List<CoinProduct> findTop5ByOrderByClosingPriceDesc();

    // coin crud
// 삭제되지 않은 금융 자산(Coin) 전체 조회
    List<Coin> findByUidAndDeleteDateIsNull(Member uid);

    // 특정 index값의 정보 조회
    Optional<Coin> findByIndexAndDeleteDateIsNull(@Param("index") Integer index);

    @Query(value = "SELECT month(c.add_date) as month, c.prod_category as category, SUM(c.avg_buy_price * c.balance) as totalAmount " +
            "FROM asset.coin c WHERE c.uid = :uid " +
            "GROUP BY month(c.add_date), c.prod_category", nativeQuery = true)
    List<Object[]> findMonthlyCoinAssets(@Param("uid") Member uid);
}

