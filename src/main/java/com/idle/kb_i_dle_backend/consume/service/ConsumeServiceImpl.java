package com.idle.kb_i_dle_backend.consume.service;

import com.idle.kb_i_dle_backend.consume.dto.CategorySumDTO;
import com.idle.kb_i_dle_backend.consume.dto.OutcomeAverageDTO;
import com.idle.kb_i_dle_backend.consume.dto.OutcomeUserDTO;
import com.idle.kb_i_dle_backend.consume.entity.OutcomeAverage;
import com.idle.kb_i_dle_backend.consume.entity.OutcomeUser;
import com.idle.kb_i_dle_backend.consume.repository.CategorySumRepository;
import com.idle.kb_i_dle_backend.consume.repository.ConsumeRepository;
import com.idle.kb_i_dle_backend.consume.repository.OutcomeUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsumeServiceImpl implements ConsumeService {

    private final ConsumeRepository consumeRepository;
    private final OutcomeUserRepository outcomeUserRepository;
    private final CategorySumRepository categorySumRepository;


    @Override
    public List<OutcomeAverageDTO> getAll() {
        // 엔티티를 DTO로 변환하여 반환
        /*
         *@params findAll
         */
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
        return categorySumRepository.findCategorySumByUidAndYearAndMonth(uid, year, month);
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
}
