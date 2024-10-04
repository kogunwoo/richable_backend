package com.idle.kb_i_dle_backend.domain.finance.service.impl;

import com.idle.kb_i_dle_backend.domain.finance.dto.CoinDTO;
import com.idle.kb_i_dle_backend.domain.finance.entity.Coin;
import com.idle.kb_i_dle_backend.domain.finance.repository.CoinRepository;
import com.idle.kb_i_dle_backend.domain.finance.service.CoinService;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import com.idle.kb_i_dle_backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinServiceImpl implements CoinService {

    private final MemberRepository memberRepository;
    private final CoinRepository coinRepository;

    @Override
    public List<CoinDTO> getCoinList() throws Exception {
        Member tempUser = memberRepository.findByUid(1).orElseThrow();
        List<Coin> coins = coinRepository.findByUidAndDeleteDateIsNull(tempUser);

        if (coins.isEmpty()) throw new NotFoundException("");

        List<CoinDTO> coinList = new ArrayList<>();
        for (Coin c : coins) {
            CoinDTO coinDTO = CoinDTO.convertToDTO(c);
            coinList.add(coinDTO);
        }

        return coinList;
    }

    @Override
    public CoinDTO addCoin(CoinDTO coinDTO) throws ParseException {
        Member tempUser = memberRepository.findByUid(1).orElseThrow();
        Coin savedCoin = coinRepository.save(CoinDTO.convertToEntity(tempUser, coinDTO));

        return CoinDTO.convertToDTO(savedCoin);
    }

    @Transactional
    @Override
    public CoinDTO updateCoin(CoinDTO coinDTO) throws ParseException {
        Member tempUser = memberRepository.findByUid(1).orElseThrow();

        // Coin 조회
        Coin isCoin = coinRepository.findByIndexAndDeleteDateIsNull(coinDTO.getIndex())
                .orElseThrow(() -> new IllegalArgumentException("Coin not found with id: " + coinDTO.getIndex()));

        // User 조회 (User 객체가 없을 경우 예외 처리)
        Member isUser = memberRepository.findByUid(tempUser.getUid())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + tempUser.getUid()));

        // Coin의 소유자가 해당 User인지 확인
        if (!isCoin.getUid().equals(isUser)) {
            throw new AccessDeniedException("You do not have permission to modify this coin.");
        }

        // 보유량만 수정되게
        isCoin.setBalance(coinDTO.getBalance());

        Coin savedCoin = coinRepository.save(isCoin);
        return CoinDTO.convertToDTO(savedCoin);
    }

    @Transactional
    @Override
    public CoinDTO deleteCoin(Integer index) throws ParseException {
        Member tempUser = memberRepository.findByUid(1).orElseThrow();

        // Coin 조회
        Coin isCoin = coinRepository.findByIndexAndDeleteDateIsNull(index)
                .orElseThrow(() -> new IllegalArgumentException("Coin not found with id: " + index));

        // User 조회 (User 객체가 없을 경우 예외 처리)
        Member isUser = memberRepository.findByUid(tempUser.getUid())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + tempUser.getUid()));

        // Coin의 소유자가 해당 User인지 확인
        if (!isCoin.getUid().equals(isUser)) {
            throw new AccessDeniedException("You do not have permission to modify this coin.");
        }

        isCoin.setDeleteDate(new Date());

        Coin savedCoin = coinRepository.save(isCoin);
        return CoinDTO.convertToDTO(savedCoin);
    }
}
