package com.idle.kb_i_dle_backend.finance.repository;

import com.idle.kb_i_dle_backend.finance.entity.Spot;
import com.idle.kb_i_dle_backend.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpotRepository extends JpaRepository<Spot, Long> {
    // deleteDate가 현재 날짜보다 이후이고, 카테고리가 특정 카테고리인 Spot의 가격 합계를 구함
    List<Spot> findByUidAndCategoryAndDeleteDateIsNull(User uid, String category);

//    //젤위에꺼
//    List<Spot> findByUidAndDelete_dateAfter(long uid, Date currentDate);
//    //마지막
//    List<Spot> findByUidAndDelete_dateAfter(long uid, String category, Date currentDate);
}
