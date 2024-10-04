package com.idle.kb_i_dle_backend.domain.finance.service.impl;

import com.idle.kb_i_dle_backend.domain.finance.dto.StockDTO;
import com.idle.kb_i_dle_backend.domain.finance.entity.Stock;
import com.idle.kb_i_dle_backend.domain.finance.repository.StockRepository;
import com.idle.kb_i_dle_backend.domain.finance.service.StockService;
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
public class StockServiceImpl implements StockService {

    private final MemberRepository memberRepository;
    private final StockRepository stockRepository;

    @Override
    public List<StockDTO> getStockList() throws Exception {
        Member tempUser = memberRepository.findByUid(1).orElseThrow();
        List<Stock> stocks = stockRepository.findByUidAndDeleteDateIsNull(tempUser);

        if (stocks.isEmpty()) throw new NotFoundException("");

        List<StockDTO> stockList = new ArrayList<>();
        for (Stock b : stocks) {
            StockDTO stockDTO = StockDTO.convertToDTO(b);
            stockList.add(stockDTO);
        }

        return stockList;
    }

    @Override
    public StockDTO addStock(StockDTO stockDTO) throws ParseException {
        Member tempUser = memberRepository.findByUid(1).orElseThrow();
        Stock savedStock = stockRepository.save(StockDTO.convertToEntity(tempUser, stockDTO));

        return StockDTO.convertToDTO(savedStock);
    }

    @Transactional
    @Override
    public StockDTO updateStock(StockDTO stockDTO) throws ParseException {
        Member tempUser = memberRepository.findByUid(1).orElseThrow();

        // Stock 조회
        Stock isStock = (Stock) stockRepository.findByIndexAndDeleteDateIsNull(stockDTO.getIndex())
                .orElseThrow(() -> new IllegalArgumentException("Stock not found with id: " + stockDTO.getIndex()));

        // User 조회 (User 객체가 없을 경우 예외 처리)
        Member isUser = memberRepository.findByUid(tempUser.getUid())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + tempUser.getUid()));

        // Stock의 소유자가 해당 User인지 확인
        if (!isStock.getUid().equals(isUser)) {
            throw new AccessDeniedException("You do not have permission to modify this stock.");
        }

        // 수량만 수정되게
        isStock.setHldgQty(stockDTO.getHldgQty());

        Stock savedStock = stockRepository.save(isStock);
        return StockDTO.convertToDTO(savedStock);
    }

    @Transactional
    @Override
    public StockDTO deleteStock(Integer index) throws ParseException {
        Member tempUser = memberRepository.findByUid(1).orElseThrow();

        // Stock 조회
        Stock isStock = (Stock) stockRepository.findByIndexAndDeleteDateIsNull(index)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found with id: " + index));

        // User 조회 (User 객체가 없을 경우 예외 처리)
        Member isUser = memberRepository.findByUid(tempUser.getUid())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + tempUser.getUid()));

        // Stock의 소유자가 해당 User인지 확인
        if (!isStock.getUid().equals(isUser)) {
            throw new AccessDeniedException("You do not have permission to modify this stock.");
        }
        isStock.setDeleteDate(new Date());

        Stock savedStock = stockRepository.save(isStock);
        return StockDTO.convertToDTO(savedStock);
    }
}
