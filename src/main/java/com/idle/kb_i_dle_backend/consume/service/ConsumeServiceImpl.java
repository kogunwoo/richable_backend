package com.idle.kb_i_dle_backend.consume.service;

import com.idle.kb_i_dle_backend.consume.dto.*;
import com.idle.kb_i_dle_backend.consume.entity.OutcomeAverage;
import com.idle.kb_i_dle_backend.consume.entity.OutcomeUser;
import com.idle.kb_i_dle_backend.consume.repository.ConsumeRepository;
import com.idle.kb_i_dle_backend.consume.repository.OutcomeUserRepository;
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
}
