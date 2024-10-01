package com.idle.kb_i_dle_backend.domain.income.service.impl;

import com.idle.kb_i_dle_backend.domain.income.dto.IncomeDTO;
import com.idle.kb_i_dle_backend.domain.income.entity.Income;
import com.idle.kb_i_dle_backend.domain.income.repository.IncomeRepository;
import com.idle.kb_i_dle_backend.domain.income.service.IncomeService;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import com.idle.kb_i_dle_backend.domain.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final UserRepository userRepository;
    private final IncomeRepository incomeRepository;

    @Override
    public List<IncomeDTO> getIncomeList() throws Exception {
        Member tempMember = userRepository.findByUid(1).orElseThrow();
        List<Income> incomes = incomeRepository.findByUid(tempMember);

        if (incomes.isEmpty()) throw new NotFoundException("");

        List<IncomeDTO> incomeList = new ArrayList<>();
        for (Income i : incomes) {
            IncomeDTO incomeDTO = IncomeDTO.convertToDTO(i);
            incomeList.add(incomeDTO);
        }

        return incomeList;
    }

    @Override
    public IncomeDTO getIncomeByIndex(Integer index) throws Exception {
        Member tempMember = userRepository.findByUid(1).orElseThrow();
        Income isIncome = incomeRepository.findByIndex(index)
                .orElseThrow(() -> new IllegalArgumentException("Income not found with index: " + index));

        // 유저가 소유한 income인지 확인
        if (!isIncome.getUid().getUid().equals(tempMember.getUid())) {
            throw new AccessDeniedException("You do not have permission to delete this income.");
        }

        return IncomeDTO.convertToDTO(isIncome);
    }

    @Override
    public IncomeDTO addIncome(IncomeDTO incomeDTO) throws ParseException {
        Member tempMember = userRepository.findByUid(1).orElseThrow();
        Income savedIncome = incomeRepository.save(IncomeDTO.convertToEntity(tempMember, incomeDTO));

        return IncomeDTO.convertToDTO(savedIncome);
    }

    @Transactional
    @Override
    public IncomeDTO updateIncome(IncomeDTO incomeDTO) throws ParseException {
        Member tempMember = userRepository.findByUid(1).orElseThrow();

        // Income 조회
        Income isIncome = incomeRepository.findByIndex(incomeDTO.getIncomeId())
                .orElseThrow(() -> new IllegalArgumentException("Income not found with id: " + incomeDTO.getIncomeId()));

        // User 조회 (User 객체가 없을 경우 예외 처리)
        Member isUser = userRepository.findByUid(tempMember.getUid())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + tempMember.getUid()));

        // income의 소유자가 해당 User인지 확인
        if (!isIncome.getUid().equals(isUser)) {
            throw new AccessDeniedException("You do not have permission to modify this income.");
        }

        isIncome.setType(incomeDTO.getType());
        isIncome.setAmount(incomeDTO.getPrice());
        isIncome.setDescript(incomeDTO.getContents());
        isIncome.setMemo(incomeDTO.getMemo());

        Income savedIncome = incomeRepository.save(isIncome);
        return IncomeDTO.convertToDTO(savedIncome);
    }

    // 특정 유저와 index에 해당하는 소득 삭제
    @Transactional
    @Override
    public Integer deleteIncomeByUidAndIndex(Integer index) {
        Member tempMember = userRepository.findByUid(1).orElseThrow();
        Income isIncome = incomeRepository.findByIndex(index)
                .orElseThrow(() -> new IllegalArgumentException("Income not found with index: " + index));

        // 유저가 소유한 소득인지 확인
        if (!isIncome.getUid().getUid().equals(tempMember.getUid())) {
            throw new AccessDeniedException("You do not have permission to delete this income.");
        }

        incomeRepository.deleteByIndex(index);  // income 삭제

        return index;
    }
}
