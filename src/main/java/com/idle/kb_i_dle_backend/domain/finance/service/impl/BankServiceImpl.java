package com.idle.kb_i_dle_backend.domain.finance.service.impl;

import com.idle.kb_i_dle_backend.domain.finance.dto.BankDTO;
import com.idle.kb_i_dle_backend.domain.finance.entity.Bank;
import com.idle.kb_i_dle_backend.domain.finance.repository.BankRepository;
import com.idle.kb_i_dle_backend.domain.finance.service.BankService;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import com.idle.kb_i_dle_backend.domain.member.repository.MemberRepository;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final MemberRepository memberRepository;
    private final BankRepository bankRepository;

    @Override
    public List<BankDTO> getBankList() throws Exception {
        Member tempUser = memberRepository.findByUid(1);
        List<Bank> banks = bankRepository.findByUidAndDeleteDateIsNull(tempUser);

        if (banks.isEmpty()) {
            throw new NotFoundException("");
        }

        List<BankDTO> bankList = new ArrayList<>();
        for (Bank b : banks) {
            BankDTO bankDTO = BankDTO.convertToDTO(b);
            bankList.add(bankDTO);
        }

        return bankList;
    }

    @Override
    public BankDTO addBank(BankDTO bankDTO) throws ParseException {
        Member tempUser = memberRepository.findByUid(1);
        Bank savedBank = bankRepository.save(BankDTO.convertToEntity(tempUser, bankDTO));

        return BankDTO.convertToDTO(savedBank);
    }

    @Transactional
    @Override
    public BankDTO updateBank(BankDTO bankDTO) throws ParseException {
        Member tempUser = memberRepository.findByUid(1);

        // Bank 조회
        Bank isBank = bankRepository.findByIndexAndDeleteDateIsNull(bankDTO.getIndex())
                .orElseThrow(() -> new IllegalArgumentException("Bank not found with id: " + bankDTO.getIndex()));

        // User 조회 (User 객체가 없을 경우 예외 처리)
        Member isUser = memberRepository.findByUid(tempUser.getUid());

        // Bank의 소유자가 해당 User인지 확인
        if (!isBank.getUid().equals(isUser)) {
            throw new AccessDeniedException("You do not have permission to modify this bank.");
        }

        // 잔액만 수정되게
        isBank.setBalanceAmt(bankDTO.getBalanceAmt());

        Bank savedBank = bankRepository.save(isBank);
        return BankDTO.convertToDTO(savedBank);
    }

    @Transactional
    @Override
    public BankDTO deleteBank(Integer index) throws ParseException {
        Member tempUser = memberRepository.findByUid(1);

        // Bank 조회
        Bank isBank = bankRepository.findByIndexAndDeleteDateIsNull(index)
                .orElseThrow(() -> new IllegalArgumentException("Bank not found with id: " + index));

        // User 조회 (User 객체가 없을 경우 예외 처리)
        Member isUser = memberRepository.findByUid(tempUser.getUid());

        // Bank의 소유자가 해당 User인지 확인
        if (!isBank.getUid().equals(isUser)) {
            throw new AccessDeniedException("You do not have permission to modify this bank.");
        }
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        dateFormat.format(new Date());
        isBank.setDeleteDate(new Date());

        Bank savedBank = bankRepository.save(isBank);
        return BankDTO.convertToDTO(savedBank);
    }
}
