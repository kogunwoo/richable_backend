package com.idle.kb_i_dle_backend.finance.service;

import com.idle.kb_i_dle_backend.finance.dto.PriceSumDTO;
import com.idle.kb_i_dle_backend.finance.dto.SpotDTO;
import com.idle.kb_i_dle_backend.finance.entity.UserSpot;
import com.idle.kb_i_dle_backend.finance.repository.UserSpotRepository;
import com.idle.kb_i_dle_backend.member.entity.Member;
import com.idle.kb_i_dle_backend.member.repository.MemberRepository;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SpotServiceImpl implements SpotService {

    @Autowired
    private UserSpotRepository userSpotRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 카테고리별 현물 자산 총합
    @Override
    public PriceSumDTO getTotalPriceByCategory(String category) throws Exception{
        Member tempMember = memberRepository.findByUid(1).orElseThrow();
        List<UserSpot> spots = userSpotRepository.findByUidAndCategoryAndDeleteDateIsNull(tempMember, category);

        if (spots.isEmpty()) throw new NotFoundException("");

        PriceSumDTO priceSum = new PriceSumDTO(
                category,
                spots.stream()
                        .mapToLong(UserSpot::getPrice)
                        .sum());

        return priceSum;
    }

    // 전체 현물 자산 총합
    @Override
    public PriceSumDTO getTotalPrice() throws Exception {
        Member tempMember = memberRepository.findByUid(1).orElseThrow();
        List<UserSpot> spots = userSpotRepository.findByUidAndDeleteDateIsNull(tempMember);

        if (spots.isEmpty()) throw new NotFoundException("");

        PriceSumDTO priceSum = new PriceSumDTO(
                "현물자산",
                spots.stream()
                        .mapToLong(UserSpot::getPrice)
                        .sum());

        return priceSum;
    }

    // 현물 자산 목록 전체 조회
    @Override
    public List<SpotDTO> getSpotList() throws Exception {
        Member tempMember = memberRepository.findByUid(1).orElseThrow();
        List<UserSpot> spots = userSpotRepository.findByUidAndDeleteDateIsNull(tempMember);

        if (spots.isEmpty()) throw new NotFoundException("");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<SpotDTO> spotList = new ArrayList<>();
        for (UserSpot s : spots) {
            SpotDTO spotDTO = new SpotDTO();
            spotDTO.setIndex(s.getIndex());
            spotDTO.setCategory(s.getCategory());
            spotDTO.setName(s.getName());
            spotDTO.setPrice(s.getPrice());
            String formattedAddDate = dateFormat.format(s.getAddDate());
            spotDTO.setAddDate(formattedAddDate);
            spotList.add(spotDTO);
        }

        return spotList;
    }

    // 현물 자산 추가
    @Override
    public SpotDTO addSpot(UserSpot spot) {
        Member tempMember = memberRepository.findByUid(1).orElseThrow();
        // User 객체를 Spot 엔티티에 설정
        spot.setUid(tempMember);
        spot.setAddDate(new Date());
        // Spot 엔티티를 저장하고 반환
        UserSpot savedSpot = userSpotRepository.save(spot);
        return convertToDTO(savedSpot);
    }

    // 현물 자산 마지막 index 값 조회
    @Override
    public Integer getLastSpotIndex() {
        Optional<UserSpot> lastSpot = userSpotRepository.findTopByOrderByIndexDesc();
        return lastSpot.map(UserSpot::getIndex).orElse(null);
    }

    // Spot -> SpotDTO 변환 메서드
    private SpotDTO convertToDTO(UserSpot spot) {
        SpotDTO spotDTO = new SpotDTO();
        spotDTO.setIndex(getLastSpotIndex());
        spotDTO.setCategory(spot.getCategory());
        spotDTO.setName(spot.getName());
        spotDTO.setPrice(spot.getPrice());

        // Date를 String으로 변환
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (spot.getAddDate() != null) {
            spotDTO.setAddDate(dateFormat.format(new Date()));
        }

        return spotDTO;
    }


    private SpotDTO convertToUpdateDTO(UserSpot spot) {
        SpotDTO spotDTO = new SpotDTO();
        spotDTO.setIndex(spot.getIndex());
        spotDTO.setCategory(spot.getCategory());
        spotDTO.setName(spot.getName());
        spotDTO.setPrice(spot.getPrice());

        // Date를 String으로 변환
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        spotDTO.setAddDate(dateFormat.format(spot.getAddDate()));

        return spotDTO;
    }

    // 현물 자산 수정
    @Transactional
    @Override
    public SpotDTO updateSpot(UserSpot spot) {
        Member tempMember = memberRepository.findByUid(1).orElseThrow();
        // Spot 조회
        UserSpot isSpot = userSpotRepository.findByIndex(spot.getIndex())
                .orElseThrow(() -> new IllegalArgumentException("Spot not found with id: " + spot.getIndex()));

        // User 조회 (User 객체가 없을 경우 예외 처리)
        Member isMember = memberRepository.findByUid(tempMember.getUid())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + tempMember.getUid()));

        // Spot의 소유자가 해당 User인지 확인
        if (!isSpot.getUid().equals(isMember)) {
            throw new AccessDeniedException("You do not have permission to modify this spot.");
        }

        isSpot.setName(spot.getName());
        isSpot.setPrice(spot.getPrice());

        UserSpot savedSpot = userSpotRepository.save(isSpot);
        return convertToUpdateDTO(savedSpot);
    }

    // 특정 유저와 index에 해당하는 Spot 삭제
    @Transactional
    @Override
    public Integer deleteSpotByUidAndIndex(Integer index) {
        Member tempMember = memberRepository.findByUid(1).orElseThrow();
        UserSpot isSpot = userSpotRepository.findByIndex(index)
                .orElseThrow(() -> new IllegalArgumentException("Spot not found with index: " + index));

        // 유저가 소유한 Spot인지 확인
        if (!isSpot.getUid().getUid().equals(tempMember.getUid())) {
            throw new AccessDeniedException("You do not have permission to delete this spot.");
        }

        userSpotRepository.deleteByIndex(index);  // Spot 삭제

        return index;
    }

}