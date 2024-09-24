package com.idle.kb_i_dle_backend.consume.service;

import com.idle.kb_i_dle_backend.consume.entity.Outcome;
import com.idle.kb_i_dle_backend.consume.repository.ConsumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service // 스프링 서비스 컴포넌트로 등록
public class ConsumeServiceImpl implements ConsumeService {

    @Autowired
    private ConsumeRepository consumeRepository;

    @Override
    public List<Outcome> findAll() {
        return consumeRepository.findAll();
    }

    // OutcomeUser에 있어야 할 메소드로 보임
    @Override
    public List<Outcome> findByUid(int uid) {
        // 'Outcome' 엔티티에서는 uid 필드가 없으므로, 실제 사용 목적에 맞게 수정해야 합니다.
        // 이 메소드는 'OutcomeUser' 리포지토리에 있어야 할 메소드일 가능성이 있습니다.
        throw new UnsupportedOperationException("Outcome 엔티티에 uid 필드가 없습니다.");
    }

    @Override
    public Outcome saveConsume(Outcome consume) {
        return consumeRepository.save(consume);
    }

    // New methods related to outcome_average fields

    @Override
    public List<Outcome> findByHouseholdHeadAgeGroup(String ageGroup) {
        return consumeRepository.findByHouseholdHeadAgeGroup(ageGroup);
    }

    @Override
    public List<Outcome> findByOutcomeExpenditureCategory(String category) {
        // 필드명 일치
        return consumeRepository.findByOutcomeExp(category);
    }

    @Override
    public List<Outcome> findByHouseholdHeadAgeGroupAndOutcomeExpenditureCategory(String ageGroup, String category) {
        // 필드명 일치
        return consumeRepository.findByHouseholdHeadAgeGroupAndOutcomeExp(ageGroup, category);
    }

    @Override
    public List<Outcome> findByHouseholdSizeGreaterThan(double size) {
        // BigDecimal 타입을 사용해야 함
        return consumeRepository.findByHouseholdSizeGreaterThan(BigDecimal.valueOf(size));
    }
}
