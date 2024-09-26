package com.idle.kb_i_dle_backend.finance.service;

import com.idle.kb_i_dle_backend.finance.entity.Spot;
import com.idle.kb_i_dle_backend.finance.repository.SpotRepository;
import com.idle.kb_i_dle_backend.member.entity.User;
import com.idle.kb_i_dle_backend.member.util.JwtProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Service
public class SpotServiceImpl implements SpotService {

    @Autowired
    private SpotRepository spotRepository;

    public Long getTotalPriceByCategory(Integer uid, String category) {

        User user = User.builder().uid(uid).build();

        Date currentDate = new Date();  // 현재 날짜 구하기

        System.out.println(user + "/" + category);

        List<Spot> spots = spotRepository.findByUidAndCategoryAndDeleteDateIsNull(user, category);

        // Java 코드로 합계를 계산
        return spots.stream()
                .mapToLong(Spot::getPrice)  // 각 Spot의 price 추출
                .sum();                     // price의 합계를 구함
    }

//    public void createSpot(String token, Spot spotDetails) {
//        // JWT에서 사용자 이름을 추출
//        Integer uid = jwtProcessor.getUid(token);
//
//        // 사용자 이름을 기반으로 User 엔티티 조회
//        Optional<User> user = userRepository.findByUsername(uid);
//
//        if (user.isPresent()) {
//            // Spot 엔티티에 User 매핑
//            Spot spot = new Spot();
//            spot.setUser(user.get());
//            spot.setCategory(spotDetails.getCategory());
//            spot.setName(spotDetails.getName());
//            spot.setPrice(spotDetails.getPrice());
//            spot.setProd_category(spotDetails.getProd_category());
//            spot.setAdd_date(spotDetails.getAdd_date());
//
//            // Spot 저장
//            spotRepository.save(spot);
//        } else {
//            // 사용자 정보가 없을 경우 처리
//            throw new IllegalArgumentException("User not found");
//        }
//    }


}