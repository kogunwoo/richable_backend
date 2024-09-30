package com.idle.kb_i_dle_backend.income.service.impl;

import com.idle.kb_i_dle_backend.income.dto.IncomeDTO;
import com.idle.kb_i_dle_backend.income.entity.Income;
import com.idle.kb_i_dle_backend.income.repository.IncomeRepository;
import com.idle.kb_i_dle_backend.income.service.IncomeService;
import com.idle.kb_i_dle_backend.member.entity.User;
import com.idle.kb_i_dle_backend.member.repository.UserRepository;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<IncomeDTO> getIncomeList() throws Exception {
        User tempUser = userRepository.findByUid(1).orElseThrow();
        List<Income> incomes = incomeRepository.findByUid(tempUser);

        if (incomes.isEmpty()) throw new NotFoundException("");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<IncomeDTO> incomeList = new ArrayList<>();
        for (Income i : incomes) {
            IncomeDTO incomeDTO = new IncomeDTO();
            incomeDTO.setIncomeId(i.getIndex());
            incomeDTO.setType(i.getType());
            String formattedDate = dateFormat.format(i.getDate());
            incomeDTO.setIncomeDate(formattedDate);
            incomeDTO.setPrice(i.getAmount());
            incomeDTO.setContents(i.getDescript());
            incomeDTO.setMemo(i.getMemo());

            incomeList.add(incomeDTO);
        }

        return incomeList;
    }

    @Override
    public IncomeDTO getIncomeByIndex(Integer index) throws Exception {
        User tempUser = userRepository.findByUid(1).orElseThrow();
        Income isIncome = incomeRepository.findByIndex(index)
                .orElseThrow(() -> new IllegalArgumentException("Income not found with index: " + index));

        // 유저가 소유한 income인지 확인
        if (!isIncome.getUid().getUid().equals(tempUser.getUid())) {
            throw new AccessDeniedException("You do not have permission to delete this income.");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(isIncome.getDate());
        IncomeDTO incomeDTO = new IncomeDTO(isIncome.getIndex(), isIncome.getType(), formattedDate, isIncome.getAmount(), isIncome.getDescript(), isIncome.getMemo());

        return incomeDTO;
    }

    @Override
    public IncomeDTO addIncome(IncomeDTO incomeDTO) throws ParseException {
        User tempUser = userRepository.findByUid(1).orElseThrow();
        Income savedIncome = incomeRepository.save(IncomeDTO.convertToEntity(tempUser, incomeDTO));

        return IncomeDTO.convertToDTO(savedIncome);
    }

    @Transactional
    @Override
    public IncomeDTO updateIncome(IncomeDTO incomeDTO) throws ParseException {
        User tempUser = userRepository.findByUid(1).orElseThrow();

        // Income 조회
        Income isIncome = incomeRepository.findByIndex(incomeDTO.getIncomeId())
                .orElseThrow(() -> new IllegalArgumentException("Income not found with id: " + incomeDTO.getIncomeId()));

        // User 조회 (User 객체가 없을 경우 예외 처리)
        User isUser = userRepository.findByUid(tempUser.getUid())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + tempUser.getUid()));

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
        User tempUser = userRepository.findByUid(1).orElseThrow();
        Income isIncome = incomeRepository.findByIndex(index)
                .orElseThrow(() -> new IllegalArgumentException("Income not found with index: " + index));

        // 유저가 소유한 소득인지 확인
        if (!isIncome.getUid().getUid().equals(tempUser.getUid())) {
            throw new AccessDeniedException("You do not have permission to delete this income.");
        }

        incomeRepository.deleteByIndex(index);  // income 삭제

        return index;
    }
}
