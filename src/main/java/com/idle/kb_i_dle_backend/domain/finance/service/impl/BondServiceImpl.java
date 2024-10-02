package com.idle.kb_i_dle_backend.domain.finance.service.impl;

import com.idle.kb_i_dle_backend.domain.finance.dto.BondDTO;
import com.idle.kb_i_dle_backend.domain.finance.entity.Bond;
import com.idle.kb_i_dle_backend.domain.finance.repository.BondRepository;
import com.idle.kb_i_dle_backend.domain.finance.service.BondService;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import com.idle.kb_i_dle_backend.domain.member.repository.UserRepository;
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
public class BondServiceImpl implements BondService {

    private final UserRepository userRepository;
    private final BondRepository bondRepository;

    @Override
    public List<BondDTO> getBondList() throws Exception {
        Member tempUser = userRepository.findByUid(1).orElseThrow();
        List<Bond> bonds = bondRepository.findByUidAndDeleteDateIsNull(tempUser);

        if (bonds.isEmpty()) throw new NotFoundException("");

        List<BondDTO> bondList = new ArrayList<>();
        for (Bond b : bonds) {
            BondDTO bondDTO = BondDTO.convertToDTO(b);
            bondList.add(bondDTO);
        }

        return bondList;
    }

    @Override
    public BondDTO addBond(BondDTO bondDTO) throws ParseException {
        Member tempUser = userRepository.findByUid(1).orElseThrow();
        Bond savedBond = bondRepository.save(BondDTO.convertToEntity(tempUser, bondDTO));

        return BondDTO.convertToDTO(savedBond);
    }

    @Transactional
    @Override
    public BondDTO updateBond(BondDTO bondDTO) throws ParseException {
        Member tempUser = userRepository.findByUid(1).orElseThrow();

        // Bond 조회
        Bond isBond = bondRepository.findByIndexAndDeleteDateIsNull(bondDTO.getIndex())
                .orElseThrow(() -> new IllegalArgumentException("Bond not found with id: " + bondDTO.getIndex()));

        // User 조회 (User 객체가 없을 경우 예외 처리)
        Member isUser = userRepository.findByUid(tempUser.getUid())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + tempUser.getUid()));

        // Bond의 소유자가 해당 User인지 확인
        if (!isBond.getUid().equals(isUser)) {
            throw new AccessDeniedException("You do not have permission to modify this bond.");
        }

        // 보유량만 수정되게
        isBond.setCnt(bondDTO.getCnt());

        Bond savedBond = bondRepository.save(isBond);
        return BondDTO.convertToDTO(savedBond);
    }

    @Transactional
    @Override
    public BondDTO deleteBond(Integer index) throws ParseException {
        Member tempUser = userRepository.findByUid(1).orElseThrow();

        // Bond 조회
        Bond isBond = bondRepository.findByIndexAndDeleteDateIsNull(index)
                .orElseThrow(() -> new IllegalArgumentException("Bond not found with id: " + index));

        // User 조회 (User 객체가 없을 경우 예외 처리)
        Member isUser = userRepository.findByUid(tempUser.getUid())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + tempUser.getUid()));

        // Bond의 소유자가 해당 User인지 확인
        if (!isBond.getUid().equals(isUser)) {
            throw new AccessDeniedException("You do not have permission to modify this bond.");
        }

        isBond.setDeleteDate(new Date());

        Bond savedBond = bondRepository.save(isBond);
        return BondDTO.convertToDTO(savedBond);
    }
}
