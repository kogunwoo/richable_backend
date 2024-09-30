package com.idle.kb_i_dle_backend.outcome.service;

import com.idle.kb_i_dle_backend.outcome.dto.*;
import com.idle.kb_i_dle_backend.outcome.entity.OutcomeAverage;
import com.idle.kb_i_dle_backend.outcome.entity.OutcomeUser;
import com.idle.kb_i_dle_backend.outcome.repository.AverageOutcomeRepository;
import com.idle.kb_i_dle_backend.outcome.repository.OutcomeUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutcomeServiceImpl implements OutcomeService {

    private final AverageOutcomeRepository averageOutcomeRepository;
    private final OutcomeUserRepository outcomeUserRepository;


    @Override
    public List<OutcomeAverageDTO> getAll() {
        // 엔티티를 DTO로 변환하여 반환
        return averageOutcomeRepository.findAll()
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
                outcomeUser.getUid().getUid(),
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
    public MonthOutcomeDTO findMonthOutcome(Integer year, Integer month) {
        List<OutcomeUser> consumes = outcomeUserRepository.findAmountAllByUidAndYearAndMonth(1, year, month);
        List<Long> dailyAmount = new ArrayList<>(Collections.nCopies(31, 0L));
        for(OutcomeUser consume : consumes) {
            Date date = consume.getDate();
            int day = date.getDate();
            System.out.println(date.toString() + " " + consume.getAmount());
            dailyAmount.set(day -1 , dailyAmount.get(day -1 ) + consume.getAmount()) ;
        }

        // 누적합 계산
        long cumulativeSum = 0L;
        for (int i = 0; i < dailyAmount.size(); i++) {
            cumulativeSum += dailyAmount.get(i);  // 이전까지의 합을 더함
            dailyAmount.set(i, cumulativeSum);    // 누적합을 다시 리스트에 저장
        }

        System.out.println(consumes.toString());
        MonthOutcomeDTO monthOutcomeDTO = new MonthOutcomeDTO(month,year,dailyAmount);

        return monthOutcomeDTO;
    }
}
