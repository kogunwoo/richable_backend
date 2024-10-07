package com.idle.kb_i_dle_backend.domain.finance.repository;

import com.idle.kb_i_dle_backend.domain.finance.entity.CoinPrice;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinPriceRepository extends JpaRepository<CoinPrice, Long> {

    @Query(value = "WITH latest_dates AS (\n" +
            "    SELECT DISTINCT date\n" +
            "    FROM product.coin_price\n" +
            "    ORDER BY date DESC\n" +
            "    LIMIT 2\n" +
            "), price_diff AS (\n" +
            "    SELECT cp.coin_name,\n" +
            "           MAX(CASE WHEN cp.date = ld.latest_date THEN cp.price END) AS latest_price,\n" +
            "           MAX(CASE WHEN cp.date = ld.previous_date THEN cp.price END) AS previous_price\n" +
            "    FROM product.coin_price cp\n" +
            "    CROSS JOIN (\n" +
            "        SELECT MAX(date) AS latest_date, MIN(date) AS previous_date\n" +
            "        FROM latest_dates\n" +
            "    ) ld\n" +
            "    WHERE cp.date IN (SELECT date FROM latest_dates)\n" +
            "    GROUP BY cp.coin_name\n" +
            ")\n" +
            "SELECT pd.coin_name, \n" +
            "       CAST(pd.latest_price AS DECIMAL(20,8)) - CAST(pd.previous_price AS DECIMAL(20,8)) AS price_difference,\n" +
            "       CAST(pd.previous_price AS DECIMAL(20,8)) AS previous_price, \n" +
            "       CAST(pd.latest_price AS DECIMAL(20,8)) AS latest_price\n" +
            "FROM price_diff pd\n" +
            "WHERE pd.latest_price IS NOT NULL AND pd.previous_price IS NOT NULL\n" +
            "ORDER BY price_difference DESC",
            nativeQuery = true)
    List<Object[]> findPriceDifferenceBetweenLastTwoDates();

    @Modifying
    @Query(value = "INSERT INTO product.coin_price (coin_name, price, date) " +
            "VALUES (:coinName, :price, :date) " , nativeQuery = true)
    void updateCoinPrice(@Param("coinName") String coinName, @Param("price") Double price, @Param("date") Date date);
}
