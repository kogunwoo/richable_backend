package com.idle.kb_i_dle_backend.finance.service;

import com.idle.kb_i_dle_backend.finance.dto.SpotDTO;
import com.idle.kb_i_dle_backend.finance.entity.UserSpot;
import com.idle.kb_i_dle_backend.finance.repository.UserSpotRepository;
import com.idle.kb_i_dle_backend.member.entity.User;
import com.idle.kb_i_dle_backend.member.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SpotServiceImpl implements SpotService {

    @Autowired
    private UserSpotRepository userSpotRepository;

    @Autowired
    private UserRepository userRepository;

    // 카테고리별 현물 자산 총합
    @Override
    public Long getTotalPriceByCategory(Integer uid, String category) {

        User user = User.builder().uid(uid).build();

        List<UserSpot> userSpots = userSpotRepository.findByUidAndCategoryAndDeleteDateIsNull(user, category);

        // Java 코드로 합계를 계산
        return userSpots.stream()
                .mapToLong(UserSpot::getPrice)  // 각 Spot의 price 추출
                .sum();                     // price의 합계를 구함
    }

    // 전체 현물 자산 총합
    @Override
    public Long getTotalPrice(Integer uid) {
        User user = User.builder().uid(uid).build();

        List<UserSpot> userSpots = userSpotRepository.findByUidAndDeleteDateIsNull(user);

        // Java 코드로 합계를 계산
        return userSpots.stream()
                .mapToLong(UserSpot::getPrice)  // 각 Spot의 price 추출
                .sum();
    }

    // 현물 자산 목록 전체 조회
    @Override
    public List<UserSpot> getSpotList(Integer uid) {
        User user = User.builder().uid(uid).build();

        List<UserSpot> userSpots = userSpotRepository.findByUidAndDeleteDateIsNull(user);

        return userSpots;
    }

    // 현물 자산 추가
    @Override
    public SpotDTO addSpot(Integer uid, UserSpot userSpot) {
        User user = new User();
        user.setUid(uid);  // uid 값만 설정
        // User 객체를 Spot 엔티티에 설정
        userSpot.setUid(user);

        userSpot.setAddDate(new Date());
        // Spot 엔티티를 저장하고 반환
        UserSpot savedUserSpot = userSpotRepository.save(userSpot);
        return convertToDTO(savedUserSpot);
    }

    // 현물 자산 마지막 index 값 조회
    @Override
    public Integer getLastSpotIndex() {
        Optional<UserSpot> lastSpot = userSpotRepository.findTopByOrderByIndexDesc();
        return lastSpot.map(UserSpot::getIndex).orElse(null);
    }

    // Spot -> SpotDTO 변환 메서드
    private SpotDTO convertToDTO(UserSpot userSpot) {
        SpotDTO spotDTO = new SpotDTO();
        spotDTO.setIndex(getLastSpotIndex());
        spotDTO.setCategory(userSpot.getCategory());
        spotDTO.setName(userSpot.getName());
        spotDTO.setPrice(userSpot.getPrice());

        // Date를 String으로 변환
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (userSpot.getAddDate() != null) {
            spotDTO.setAddDate(dateFormat.format(new Date()));
        }

        return spotDTO;
    }


    private SpotDTO convertToUpdateDTO(UserSpot userSpot) {
        SpotDTO spotDTO = new SpotDTO();
        spotDTO.setIndex(userSpot.getIndex());
        spotDTO.setCategory(userSpot.getCategory());
        spotDTO.setName(userSpot.getName());
        spotDTO.setPrice(userSpot.getPrice());

        // Date를 String으로 변환
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        spotDTO.setAddDate(dateFormat.format(userSpot.getAddDate()));

        return spotDTO;
    }

    // 현물 자산 수정
    @Transactional
    @Override
    public SpotDTO updateSpot(Integer uid, UserSpot userSpot) {
        // Spot 조회
        UserSpot isUserSpot = userSpotRepository.findByIndex(userSpot.getIndex())
                .orElseThrow(() -> new IllegalArgumentException("Spot not found with id: " + userSpot.getIndex()));

        // User 조회 (User 객체가 없을 경우 예외 처리)
        User isUser = userRepository.findByUid(uid)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + uid));

        // Spot의 소유자가 해당 User인지 확인
        if (!isUserSpot.getUid().equals(isUser)) {
            throw new AccessDeniedException("You do not have permission to modify this spot.");
        }

        isUserSpot.setName(userSpot.getName());
        isUserSpot.setPrice(userSpot.getPrice());

        UserSpot savedUserSpot = userSpotRepository.save(isUserSpot);
        return convertToUpdateDTO(savedUserSpot);
    }

    // 특정 유저와 index에 해당하는 Spot 삭제
    @Transactional
    @Override
    public void deleteSpotByUidAndIndex(Integer uid, Integer index) {
        UserSpot isUserSpot = userSpotRepository.findByIndex(index)
                .orElseThrow(() -> new IllegalArgumentException("Spot not found with index: " + index));

        // 유저가 소유한 Spot인지 확인
        if (!isUserSpot.getUid().getUid().equals(uid)) {
            throw new AccessDeniedException("You do not have permission to delete this spot.");
        }

        userSpotRepository.deleteByIndex(index);  // Spot 삭제
    }

}