package com.idle.kb_i_dle_backend.domain.finance.service.impl;

import com.idle.kb_i_dle_backend.domain.finance.dto.CoinDTO;
import com.idle.kb_i_dle_backend.domain.finance.entity.Coin;
import com.idle.kb_i_dle_backend.domain.finance.repository.CoinRepository;
import com.idle.kb_i_dle_backend.domain.finance.service.CoinService;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import com.idle.kb_i_dle_backend.domain.member.service.MemberService;
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
public class CoinServiceImpl implements CoinService {

    private final MemberService memberService;
    private final CoinRepository coinRepository;

    @Override
    public List<CoinDTO> getCoinList() throws Exception {
        Member member = memberService.findMemberByUid(1);
        List<Coin> coins = coinRepository.findByUidAndDeleteDateIsNull(member);

        if (coins.isEmpty()) {
            throw new NotFoundException("");
        }

        List<CoinDTO> coinList = new ArrayList<>();
        for (Coin c : coins) {
            CoinDTO coinDTO = CoinDTO.convertToDTO(c);
            coinList.add(coinDTO);
        }

        return coinList;
    }

    @Override
    public CoinDTO addCoin(CoinDTO coinDTO) throws ParseException {
        Member member = memberService.findMemberByUid(1);
        Coin savedCoin = coinRepository.save(CoinDTO.convertToEntity(member, coinDTO));

        return CoinDTO.convertToDTO(savedCoin);
    }

    @Transactional
    @Override
    public CoinDTO updateCoin(CoinDTO coinDTO) throws ParseException {
        Member member = memberService.findMemberByUid(1);

        // Coin 조회
        Coin isCoin = coinRepository.findByIndexAndDeleteDateIsNull(coinDTO.getIndex())
                .orElseThrow(() -> new IllegalArgumentException("Coin not found with id: " + coinDTO.getIndex()));

        // Coin의 소유자가 해당 User인지 확인
        if (!isCoin.getUid().equals(member)) {
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
        Member member = memberService.findMemberByUid(1);

        // Coin 조회
        Coin isCoin = coinRepository.findByIndexAndDeleteDateIsNull(index)
                .orElseThrow(() -> new IllegalArgumentException("Coin not found with id: " + index));

        // Coin의 소유자가 해당 User인지 확인
        if (!isCoin.getUid().equals(member)) {
            throw new AccessDeniedException("You do not have permission to modify this coin.");
        }

        isCoin.setDeleteDate(new Date());

        Coin savedCoin = coinRepository.save(isCoin);
        return CoinDTO.convertToDTO(savedCoin);
    }
}
