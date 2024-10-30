package com.idle.kb_i_dle_backend.domain.income.service.impl;

import com.idle.kb_i_dle_backend.config.exception.CustomException;
import com.idle.kb_i_dle_backend.domain.finance.repository.AssetSummaryRepository;
import com.idle.kb_i_dle_backend.domain.income.dto.IncomeDTO;
import com.idle.kb_i_dle_backend.domain.income.entity.Income;
import com.idle.kb_i_dle_backend.domain.income.repository.IncomeRepository;
import com.idle.kb_i_dle_backend.domain.income.service.IncomeService;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import com.idle.kb_i_dle_backend.domain.member.service.MemberService;
import com.idle.kb_i_dle_backend.global.codes.ErrorCode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final MemberService memberService;
    private final IncomeRepository incomeRepository;
    private final AssetSummaryRepository assetSummaryRepository;

    private void validateOwnership(Member incomeOwner, Member requestUser) {
        if (!incomeOwner.getUid().equals(requestUser.getUid())) {
            throw new CustomException(ErrorCode.INVALID_OWNER, "You do not have permission to access this income.");
        }
    }

    @Override
    public List<IncomeDTO> getIncomeList(Integer uid) {
        try {
            Member tempMember = memberService.findMemberByUid(uid);
            List<Income> incomes = incomeRepository.findByUid(tempMember);

            if (incomes.isEmpty()) {
                throw new CustomException(ErrorCode.INVALID_INCOME, "No income data found.");
            }

            List<IncomeDTO> incomeList = new ArrayList<>();
            for (Income i : incomes) {
                IncomeDTO incomeDTO = IncomeDTO.convertToDTO(i);
                incomeList.add(incomeDTO);
            }

            return incomeList;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to retrieve income list.", e);
        }
    }

    @Override
    public long getIncomeSumInMonth(Integer uid, int year, int month) {
        try {
            Member tempUser = memberService.findMemberByUid(uid);
            List<Income> incomes = incomeRepository.findByUidAndYearAndMonth(tempUser, year, month);
            return incomes.stream().mapToLong(Income::getAmount).sum();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to calculate monthly income sum.", e);
        }
    }

    @Override
    public IncomeDTO getIncomeByIndex(Integer uid, Integer index) {
        try {
            Member tempMember = memberService.findMemberByUid(uid);
            Income isIncome = incomeRepository.findByIndex(index)
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INCOME, "Income not found with index: " + index));

            validateOwnership(isIncome.getUid(), tempMember);
            return IncomeDTO.convertToDTO(isIncome);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to retrieve income by index.", e);
        }
    }

    @Override
<<<<<<< HEAD
    public IncomeDTO addIncome(Integer uid, IncomeDTO incomeDTO) {
        try {
            Member tempMember = memberService.findMemberByUid(uid);
            Income savedIncome = incomeRepository.save(IncomeDTO.convertToEntity(tempMember, incomeDTO));
            assetSummaryRepository.insertOrUpdateAssetSummary(uid);

            return IncomeDTO.convertToDTO(savedIncome);
        } catch (ParseException e) {
            throw new CustomException(ErrorCode.PARSE_ERROR, "Failed to parse income date.", e);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to add income.", e);
        }
=======
    public IncomeDTO addIncome(Integer uid, IncomeDTO incomeDTO) throws ParseException {
        Member tempMember = memberService.findMemberByUid(uid);
        Income savedIncome = incomeRepository.save(IncomeDTO.convertToEntity(tempMember, incomeDTO));
        assetSummaryRepository.insertOrUpdateAssetSummary(uid);
        //assetSummaryRepository.deleteDuplicateAssetSummary();
        return IncomeDTO.convertToDTO(savedIncome);
>>>>>>> d978d5daab6d080657dff15962936675374b6814
    }

    @Transactional
    @Override
    public IncomeDTO updateIncome(Integer uid, IncomeDTO incomeDTO) {
        try {
            Member member = memberService.findMemberByUid(uid);
            Income isIncome = incomeRepository.findByIndex(incomeDTO.getIncomeId())
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INCOME, "Income not found with id: " + incomeDTO.getIncomeId()));

            validateOwnership(isIncome.getUid(), member);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            isIncome.setType(incomeDTO.getType());
            isIncome.setDate(dateFormat.parse(incomeDTO.getIncomeDate()));
            isIncome.setAmount(incomeDTO.getPrice());
            isIncome.setDescript(incomeDTO.getContents());
            isIncome.setMemo(incomeDTO.getMemo());

            Income savedIncome = incomeRepository.save(isIncome);
            assetSummaryRepository.insertOrUpdateAssetSummary(uid);
            return IncomeDTO.convertToDTO(savedIncome);
        } catch (ParseException e) {
            throw new CustomException(ErrorCode.PARSE_ERROR, "Failed to parse income date.", e);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to update income.", e);
        }
<<<<<<< HEAD
=======

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        isIncome.setType(incomeDTO.getType());
        isIncome.setDate(dateFormat.parse(incomeDTO.getIncomeDate()));
        isIncome.setAmount(incomeDTO.getPrice());
        isIncome.setDescript(incomeDTO.getContents());
        isIncome.setMemo(incomeDTO.getMemo());

        Income savedIncome = incomeRepository.save(isIncome);
        assetSummaryRepository.insertOrUpdateAssetSummary(uid);
        //assetSummaryRepository.deleteDuplicateAssetSummary();
        return IncomeDTO.convertToDTO(savedIncome);
>>>>>>> d978d5daab6d080657dff15962936675374b6814
    }

    @Transactional
    @Override
    public Integer deleteIncomeByUidAndIndex(Integer uid, Integer index) {
        try {
            Member tempMember = memberService.findMemberByUid(uid);
            Income isIncome = incomeRepository.findByIndex(index)
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INCOME, "Income not found with index: " + index));

            validateOwnership(isIncome.getUid(), tempMember);

            incomeRepository.deleteByIndex(index);
            assetSummaryRepository.insertOrUpdateAssetSummary(uid);

            return index;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to delete income.", e);
        }
<<<<<<< HEAD
=======

        incomeRepository.deleteByIndex(index);  // income 삭제
        assetSummaryRepository.insertOrUpdateAssetSummary(uid);
        //assetSummaryRepository.deleteDuplicateAssetSummary();

        return index;
>>>>>>> d978d5daab6d080657dff15962936675374b6814
    }
}
