package com.idle.kb_i_dle_backend.domain.finance.service;

import com.idle.kb_i_dle_backend.domain.finance.dto.FinancialSumDTO;
import com.idle.kb_i_dle_backend.domain.finance.entity.Bank;
import com.idle.kb_i_dle_backend.domain.finance.repository.*;
import com.idle.kb_i_dle_backend.domain.income.repository.IncomeRepository;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import com.idle.kb_i_dle_backend.domain.member.repository.MemberRepository;
import com.idle.kb_i_dle_backend.domain.outcome.repository.OutcomeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FinanceServiceImplTest {
    @Mock
    private BankRepository bankRepository;
    @Mock
    private BondRepository bondRepository;
    @Mock
    private CoinRepository coinRepository;
    @Mock
    private SpotRepository spotRepository;
    @Mock
    private StockRepository stockRepository;
    @Mock
    private IncomeRepository incomeRepository;
    @Mock
    private OutcomeUserRepository outcomeUserRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private AssetSummaryRepository assetSummaryRepository;

    @InjectMocks
    private FinanceServiceImpl financeServiceImpl;

    private Member testMember;
    private Bank testBank;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 테스트 멤버 설정
        testMember = new Member();
        testMember.setUid(1);

        // 테스트 은행 자산 설정
        testBank = new Bank();
        testBank.setBalanceAmt(10000L);
    }

    @Test
    void testGetFinancialAssetsSum() {
        // Given
        when(memberRepository.findByUid(1)).thenReturn(Optional.of(testMember));

        // 은행 자산 모킹
        when(bankRepository.findAllByUidAndDeleteDateIsNull(Optional.of(testMember)))
                .thenReturn(Collections.singletonList(testBank));

        // When
        FinancialSumDTO result = financeServiceImpl.getFinancialAssetsSum(1);

        // Then
        BigDecimal expectedSum = BigDecimal.valueOf(10000L); // 은행 자산 값과 일치하도록 설정
        assertEquals(expectedSum, result);
    }
}