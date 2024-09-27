package com.idle.kb_i_dle_backend.consume.service;

import com.idle.kb_i_dle_backend.consume.dto.*;
import com.idle.kb_i_dle_backend.consume.entity.OutcomeAverage;
import com.idle.kb_i_dle_backend.consume.entity.OutcomeUser;
//import com.idle.kb_i_dle_backend.consume.repository.AvgCategorySumRepository;
import com.idle.kb_i_dle_backend.consume.repository.CategoryComRepository;
import com.idle.kb_i_dle_backend.consume.repository.ConsumeRepository;
import com.idle.kb_i_dle_backend.consume.repository.OutcomeUserRepository;
import com.idle.kb_i_dle_backend.member.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsumeServiceImpl implements ConsumeService {

    private final ConsumeRepository consumeRepository;
    private final OutcomeUserRepository outcomeUserRepository;
    private final CategoryComRepository categoryComRepository;
    private final UserInfoRepository userInfoRepository;


    @Override
    public List<OutcomeAverageDTO> getAll() {
        // 엔티티를 DTO로 변환하여 반환
        return consumeRepository.findAll()
                .stream()
                .map(this::convertToOutcomeAverageDTO)  // 엔티티를 DTO로 변환
                .collect(Collectors.toList());
    }

    // OutcomeAverage 엔티티를 OutcomeAverageDTO로 변환하는 메서드
    private OutcomeAverageDTO convertToOutcomeAverageDTO(OutcomeAverage outcomeAverage) {
        return new OutcomeAverageDTO(
                outcomeAverage.getIndex(),
                outcomeAverage.getAgeGroup(),  // getter 사용
                outcomeAverage.getCategory(),  // getter 사용
                outcomeAverage.getOutcome(),
                outcomeAverage.getHouseholdSize(),
                outcomeAverage.getQuater()  // getter 사용
        );
    }

    @Override
    public List<OutcomeUserDTO> getAllUser() {
        return outcomeUserRepository.findAll()
                .stream()
                .map(this::convertToOutcomeUserDTO)  // 엔티티를 DTO로 변환
                .collect(Collectors.toList());
    }

    @Override
    public List<CategorySumDTO> getCategorySum(int uid, int year, int month) {
        return outcomeUserRepository.findCategorySumByUidAndYearAndMonth(uid, year, month);
    }

    private OutcomeUserDTO convertToOutcomeUserDTO(OutcomeUser outcomeUser) {
        return new OutcomeUserDTO(
                outcomeUser.getIndex(),
                outcomeUser.getUid(),
                outcomeUser.getCategory(),
                outcomeUser.getDate(),
                outcomeUser.getAmount(),
                outcomeUser.getDescript(),
                outcomeUser.getMemo()
        );
    }
    @Override
    public ResponseCategorySumListDTO findCategorySum(Integer year, Integer month) {
        List<CategorySumDTO> categorySumDTOS = outcomeUserRepository.findCategorySumByUidAndYearAndMonth(1 , year, month);
        Long sum = categorySumDTOS.stream().mapToLong(CategorySumDTO::getSum).sum();
        return new ResponseCategorySumListDTO(categorySumDTOS, sum);
    }

    @Override
    public MonthConsumeDTO findMonthConsume(Integer year, Integer month) {
        List<Long> prices = outcomeUserRepository.findAmountAllByUidAndYearAndMonth(1, year, month);

        LocalDate now = LocalDate.now();
        MonthConsumeDTO monthConsumeDTO = new MonthConsumeDTO(month,year,now.toString(),prices);

        return monthConsumeDTO;
    }

    @Override
    public List<AvgCategorySumDTO> findCompareWithAvg(Integer uid, String category, Integer year, Integer month) {
        return List.of();
    }

//    @Override
//    public List<AvgCategorySumDTO> findCompareWithAvg(Integer uid, String category, Integer year, Integer month) {
//        //user_info repository에서 uid를 주고, 나이를 알아와야
//        Integer brithYear = userInfoRepository.findByuid();
//        //year랑 month로  => STring 값인 "2024년 1분기"
//        categoryComRepository.findByAgeGroupAndCategoryAndQuater();
//        //해당 카테고리의 평균 소비
//        //=> avgCategorySumDTO에 넣어
//        //유저의 해당 년도 해당 달의 해당카테고리의 소비
//        //repo에서 가져와서
//        //=> avgcategorysumDTO에 넣어야
//    }
//
//
//    @Override
//    public CategoryComDTO findCategoryCom(Integer year, Integer month) {
//        return null;
//    }
//
//    public List<AvgCategorySumDTO> getAvgCategorySum(int uid, int year, int month) {
//
//    }
}
