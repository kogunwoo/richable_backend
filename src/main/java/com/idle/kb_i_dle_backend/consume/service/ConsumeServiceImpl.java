//package com.idle.kb_i_dle_backend.consume.service;
//
//import com.idle.kb_i_dle_backend.consume.entity.Outcome;
//import com.idle.kb_i_dle_backend.consume.entity.OutcomeUser;
//import com.idle.kb_i_dle_backend.consume.repository.ConsumeRepository;
//import com.idle.kb_i_dle_backend.consume.repository.OutcomeUserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Service // 스프링 서비스 컴포넌트로 등록
//public class ConsumeServiceImpl implements ConsumeService {
//
//    @Autowired
//    private ConsumeRepository consumeRepository;
//
//    @Autowired
//    private OutcomeUserRepository outcomeuserRepository;
//
//    @Override
//    public List<Outcome> findAll() {
//        return consumeRepository.findAll();
//    }
//
//    // OutcomeUser에 있어야 할 메소드로 보임
//    @Override
//    public List<OutcomeUser> findByUid(int uid) {
//        return outcomeuserRepository.findByUid(uid);
//    }
//
//    @Override
//    public Outcome saveConsume(Outcome consume) {
//        return consumeRepository.save(consume);
//    }
//
//    // New methods related to outcome_average fields
//
//    @Override
//    public List<Outcome> findByHouseholdHeadAgeGroup(String ageGroup) {
//        return consumeRepository.findByHouseholdHeadAgeGroup(ageGroup);
//    }
//
//    @Override
//    public List<Outcome> findByOutcomeExpenditureCategory(String category) {
//        // 필드명 일치
//        return consumeRepository.findByOutcomeExp(category);
//    }
//
//    @Override
//    public List<Outcome> findByHouseholdHeadAgeGroupAndOutcomeExpenditureCategory(String ageGroup, String category) {
//        // 필드명 일치
//        return consumeRepository.findByHouseholdHeadAgeGroupAndOutcomeExp(ageGroup, category);
//    }
//
//    @Override
//    public List<Outcome> findByHouseholdSizeGreaterThan(double size) {
//        // BigDecimal 타입을 사용해야 함
//        return consumeRepository.findByHouseholdSizeGreaterThan(BigDecimal.valueOf(size));
//    }
//}
