package com.idle.kb_i_dle_backend.consume.service;

import com.idle.kb_i_dle_backend.consume.dto.AvgCategorySumDTO;
import com.idle.kb_i_dle_backend.consume.dto.CategorySumDTO;
import com.idle.kb_i_dle_backend.consume.dto.OutcomeAverageDTO;
import com.idle.kb_i_dle_backend.consume.dto.OutcomeUserDTO;
import com.idle.kb_i_dle_backend.consume.entity.OutcomeAverage;
import com.idle.kb_i_dle_backend.consume.entity.OutcomeUser;
import com.idle.kb_i_dle_backend.consume.repository.AvgCategorySumRepository;
import com.idle.kb_i_dle_backend.consume.repository.CategorySumRepository;
import com.idle.kb_i_dle_backend.consume.repository.ConsumeRepository;
import com.idle.kb_i_dle_backend.consume.repository.OutcomeUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumeServiceImpl implements ConsumeService {

    private final ConsumeRepository consumeRepository;
    private final OutcomeUserRepository outcomeUserRepository;
    private final CategorySumRepository categorySumRepository;
    private final AvgCategorySumRepository avgCategorySumRepository;

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

    @Override
    public List<AvgCategorySumDTO> getAvgCategorySum(String quater) {
        // 로그 추가 - 입력된 quater 값 확인
        log.info("Received quater: " + quater);

        // "202402" 형식을 "2024년 2분기"로 변환
        String formattedQuater = formatQuater(quater);

        // 로그 추가 - 변환된 quater 값 확인
        log.info("Formatted quater: " + formattedQuater);

        // 쿼리 실행 전 로그 출력
        log.info("Executing query for formatted quater: " + formattedQuater);

        return avgCategorySumRepository.findCategorySumByQuater(formattedQuater);
    }

    private String formatQuater(String quater) {
        // "202402" 형식이 6자리가 아니면 예외 발생
        if (quater == null || quater.length() != 6) {
            throw new IllegalArgumentException("Invalid quater format, expected format is YYYYMM.");
        }

        String year = quater.substring(0, 4);
        String quarter = quater.substring(4, 6);

        String formattedQuater;
        switch (quarter) {
            case "01":
            case "02":
            case "03":
                formattedQuater = year + "년 1분기";
                break;
            case "04":
            case "05":
            case "06":
                formattedQuater = year + "년 2분기";  // 수정된 부분
                break;
            case "07":
            case "08":
            case "09":
                formattedQuater = year + "년 3분기";
                break;
            case "10":
            case "11":
            case "12":
                formattedQuater = year + "년 4분기";
                break;
            default:
                throw new IllegalArgumentException("Invalid quarter format: " + quarter);
        }

        log.info("Formatted quater value: " + formattedQuater);

        return formattedQuater;
    }

}