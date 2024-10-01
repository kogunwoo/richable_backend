package com.idle.kb_i_dle_backend.domain.invest.service.impl;

import com.idle.kb_i_dle_backend.domain.finance.entity.UserBank;
import com.idle.kb_i_dle_backend.domain.finance.repository.BankRepository;
import com.idle.kb_i_dle_backend.domain.invest.dto.InvestDTO;
import com.idle.kb_i_dle_backend.domain.invest.service.InvestService;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import com.idle.kb_i_dle_backend.domain.member.repository.UserRepository;
import com.idle.kb_i_dle_backend.domain.member.util.JwtProcessor;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InvestServiceImpl implements InvestService {
    private final UserRepository userRepository;
    private final BankRepository bankRepository;

    public InvestServiceImpl(BankRepository bankRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public List<InvestDTO> getInvestList() throws Exception {
        Date day = new Date();
        Member tempUser = userRepository.findByUid(1).orElseThrow();
        List<UserBank> userBanks = bankRepository.findInvestmentsByUidAndDate(1, day);
        return InvestDTO.fromUserBankList(userBanks);
    }
}