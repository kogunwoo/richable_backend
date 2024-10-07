package com.idle.kb_i_dle_backend.domain.outcome.service.impl;

import com.idle.kb_i_dle_backend.domain.income.service.IncomeService;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import com.idle.kb_i_dle_backend.domain.member.repository.MemberRepository;
import com.idle.kb_i_dle_backend.domain.member.service.MemberService;
import com.idle.kb_i_dle_backend.domain.outcome.dto.*;
import com.idle.kb_i_dle_backend.domain.outcome.entity.OutcomeAverage;
import com.idle.kb_i_dle_backend.domain.outcome.entity.OutcomeCategory;
import com.idle.kb_i_dle_backend.domain.outcome.entity.OutcomeUser;
import com.idle.kb_i_dle_backend.domain.outcome.repository.AverageOutcomeRepository;
import com.idle.kb_i_dle_backend.domain.outcome.repository.CategoryRepository;
import com.idle.kb_i_dle_backend.domain.outcome.repository.OutcomeUserRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;

import com.idle.kb_i_dle_backend.domain.outcome.service.OutcomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import java.text.ParseException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutcomeServiceImpl implements OutcomeService {

    private final AverageOutcomeRepository averageOutcomeRepository;
    private final OutcomeUserRepository outcomeUserRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final IncomeService incomeService;


    /**
     * 카테고리마다 소비 합 과 전체 소비 합 조회
     * @param year
     * @param month
     * @return
     */
    @Override
    public ResponseCategorySumListDTO findCategorySum(Integer year, Integer month) {
        Optional<Member> optionalMember = memberService.findMemberByUid(1);
        if(optionalMember.isPresent()) {
            Member member = optionalMember.get();
            List<CategorySumDTO> categorySumDTOS = outcomeUserRepository.findCategorySumByUidAndYearAndMonth(member, year, month);
            Long sum = categorySumDTOS.stream().mapToLong(CategorySumDTO::getSum).sum();
            return new ResponseCategorySumListDTO(categorySumDTOS, sum);
        }
        return new ResponseCategorySumListDTO(new ArrayList<>(), 0L);
    }


    /**
     * 해당 달의 소비 누계
     * @param year
     * @param month
     * @return
     */
    @Override
    public MonthOutcomeDTO findMonthOutcome(Integer year, Integer month) {
        Optional<Member> optionalUser = memberService.findMemberByUid(1);
        MonthOutcomeDTO monthOutcomeDTO;
        if(optionalUser.isPresent()) {
            Member user = optionalUser.get();
            List<OutcomeUser> consumes = outcomeUserRepository.findAmountAllByUidAndYearAndMonth(user, year, month);
            List<Long> dailyAmount = new ArrayList<>(Collections.nCopies(31, 0L));
            for(OutcomeUser consume : consumes) {
                Date date = consume.getDate();
                int day = date.getDate();
                System.out.println(date.toString() + " " + consume.getAmount());
                dailyAmount.set(day -1 , dailyAmount.get(day -1 ) + consume.getAmount()) ;
            }

            // 누적합 계산
            long cumulativeSum = 0L;
            for (int i = 0; i < dailyAmount.size(); i++) {
                cumulativeSum += dailyAmount.get(i);  // 이전까지의 합을 더함
                dailyAmount.set(i, cumulativeSum);    // 누적합을 다시 리스트에 저장
            }

            System.out.println(consumes.toString());
            monthOutcomeDTO = new MonthOutcomeDTO(month,year,dailyAmount);

            return monthOutcomeDTO;
        }
        monthOutcomeDTO = new MonthOutcomeDTO(month,year,Collections.emptyList());
        return monthOutcomeDTO;
    }

    /**
     * start에서 end까지 해당 카테고리의 소비량을 평균과 비교
     * @param uid
     * @param year
     * @param month
     * @param category
     * @return
     */
    @Override
    @Transactional
    public CompareAverageCategoryOutcomeDTO compareWithAverage(int uid, int year, int month, String category) {
        //먼저 사용자 uid를 가져오고
        Optional<Member> optionalUser = memberService.findMemberByUid(uid);
        //dto 생성
        CompareAverageCategoryOutcomeDTO compareAverageCategoryOutcomeDTO = new CompareAverageCategoryOutcomeDTO();

        if(optionalUser.isPresent()) {
            Member user = optionalUser.get();
            //사용자의 나이대를 가져온다.
            String ageRange =  getUserAgeRange(getUserAge(user.getBirth_year()));
            return getCompareAverageCategoryOutcomeDTO(user, year, month, category, ageRange, compareAverageCategoryOutcomeDTO);
        }
        return compareAverageCategoryOutcomeDTO;

    }


    /**
     * start에서 end까지 아낄 수 잇었던 비용
     * @param uid
     * @param year
     * @param month
     * @return
     */
    @Override
    public PossibleSaveOutcomeInMonthDTO findPossibleSaveOutcome(Integer uid, int year, int month) {
        //먼저 사용자 uid를 가져오고
        Optional<Member> optionalUser = memberService.findMemberByUid(uid);
        //dto 생성
        PossibleSaveOutcomeInMonthDTO possibleSaveOutcomeInMonthDTO = new PossibleSaveOutcomeInMonthDTO();

        if(optionalUser.isPresent()) {
            Member user = optionalUser.get();
            //사용자의 나이대를 가져온다.
            String ageRange =  getUserAgeRange(getUserAge(user.getBirth_year()));

            //전체 소비 조회
            List<OutcomeUser> outcomeUsers = outcomeUserRepository.findAmountAllByUidAndYearAndMonth(user, year, month);

            //평균 전체 소비
            List<OutcomeAverage> outcomeAverages = averageOutcomeRepository.findByAgeGroupAndQuater(ageRange, getQuarter(year, month));

            Long userSum = outcomeUsers.stream().mapToLong(OutcomeUser::getAmount).sum();
            Long averageSum = outcomeAverages.stream().mapToLong(OutcomeAverage::getOutcome).sum();
            log.info("userSum = " + userSum);
            log.info("averageSum = " + averageSum);
            possibleSaveOutcomeInMonthDTO.setPossibleSaveAmount(averageSum - userSum);
            return possibleSaveOutcomeInMonthDTO;
        }
        return possibleSaveOutcomeInMonthDTO;
    }

    @Override
    public Simulation6MonthDTO calculate6MonthSaveOutcome(Integer uid, int year, int month) throws Exception {
        Simulation6MonthDTO simulation6MonthDTO = new Simulation6MonthDTO();
        //이번달 소득에서 소비 빼서 적축 량을 알아냄
        int monthPeriod = 6;
        //이번달 소득
        long monthIncome = incomeService.getIncomeSumInMonth(uid, year, month);

        Optional<Member> optionUser = memberService.findMemberByUid(uid);
        if(optionUser.isPresent()) {
            Member user = optionUser.get();
            List<OutcomeUser> outcomeUsers = outcomeUserRepository.findAmountAllByUidAndYearAndMonth(user , year, month);
            Long monthOutcome = outcomeUsers.stream().mapToLong(OutcomeUser::getAmount).sum();
            //6개월간 누적합으로 saveAmount
            long save = monthIncome - monthOutcome;
            Long[] saveAmount = new Long[monthPeriod ];
            for(int monthIndex = 0; monthIndex < monthPeriod; monthIndex++) {
                if(monthIndex == 0){
                    saveAmount[monthIndex] = save;
                    continue;
                }
                saveAmount[monthIndex] = save + saveAmount[monthIndex - 1];
            }

            simulation6MonthDTO.setSaveAmount(Arrays.asList(saveAmount));
            //거기다가 이번달 아낄 수 있엇던 비용 더해서 PossibleSaveAmount
            long possibleSave = findPossibleSaveOutcome(uid, year, month).getPossibleSaveAmount();
            List<Long> possibleSaveAmounts = new ArrayList<>();

            for(int monthIndex = 0; monthIndex < monthPeriod; monthIndex++) {
                possibleSaveAmounts.add(saveAmount[monthIndex] + possibleSave);
            }

            simulation6MonthDTO.setPossibleSaveAmount(possibleSaveAmounts);
            //6개월 날짜
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(year+ "-" +month+ "-01");
            String[] periods = new String[monthPeriod];
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM");
            for (int monthIndex = 0; monthIndex < monthPeriod; monthIndex++) {
                periods[monthIndex] = sdf2.format(calendar.getTime());
                calendar.add(Calendar.MONTH, 1);  // 한 달씩 더함
            }
            simulation6MonthDTO.setMonths(Arrays.asList(periods));
            return simulation6MonthDTO;
        }
        return simulation6MonthDTO;

    }

    /**
     * 해당 카테고리, 나이대, start~end까지의 user의 평균 소비량 비교DTO를 가져온다.
     * @param user
     * @param year
     * @param month
     * @param category
     * @param ageRange
     * @param compareAverageCategoryOutcomeDTO
     * @return
     */
    public CompareAverageCategoryOutcomeDTO getCompareAverageCategoryOutcomeDTO(Member user, int year, int month, String category, String ageRange, CompareAverageCategoryOutcomeDTO compareAverageCategoryOutcomeDTO){


        //카테고리 조회
        OutcomeCategory outcomeCategory = categoryRepository.findByCategoryNameStartingWith(category);

        //사용자의 한달 동안 소비 조회
        List<OutcomeUser> outcomeUsers = outcomeUserRepository.findAllByUidAndYearAndMonthAndCategory(user, year, month, outcomeCategory.getCategoryName());

        //date를 분기로 변경
        //사용자 나이대의 카테고리의 소비를 조회
        List<OutcomeAverage> outcomeAverages = averageOutcomeRepository.findByCategoryAndQuaterAndAgeGroup(outcomeCategory.getCategoryName(), getQuarter(year, month), ageRange);
        String categoryName = outcomeCategory.getCategoryName();
        compareAverageCategoryOutcomeDTO.setMySum( outcomeUsers.stream().mapToLong(OutcomeUser::getAmount).sum());
        compareAverageCategoryOutcomeDTO.setAverageSum(outcomeAverages.stream().mapToLong(OutcomeAverage::getOutcome).sum());
        compareAverageCategoryOutcomeDTO.setCategory(outcomeCategory.getCategoryName());
        return compareAverageCategoryOutcomeDTO;
    }

    /**
     * 사용자 나이대를 가져옴
     * @param age
     * @return
     */
    private String getUserAgeRange(int age){
        if(age <  40){ //30대 이하
            return "30대 이하";
        } else if(age < 50) { //40대
            return "40대";
        } else if( age < 60) { //50 대
            return "50대";
        } else if( age < 65) { //60세 이상
            return "60세이상 가구";
        } // 65세 이상
        return "65세이상 가구";
    }

    /**
     * 사용자의 나이를 가져옴
     * @param birthYear
     * @return
     */
    private int getUserAge(int birthYear){
        LocalDate birthDate = LocalDate.of(birthYear, 1, 1);
        LocalDate currentDate = LocalDate.now();

        return Period.between(birthDate , currentDate).getYears();
    }

    /**
     * 몇 분기인지 반환
     * @param
     * @return
     */
    private String getQuarter(int year, int month) {
        StringBuffer sb = new StringBuffer();
        return sb.append(year).append("년 ").append(month/ 4 + 1).append("분기").toString();
    }

    // 소비 CRUD 추가

    @Override
    public List<OutcomeUserDTO> getOutcomeList() throws Exception {
        Member tempMember = memberRepository.findByUid(1).orElseThrow();
        List<OutcomeUser> outcomes = outcomeUserRepository.findByUid(tempMember);

        if (outcomes.isEmpty()) throw new NotFoundException("");

        List<OutcomeUserDTO> outcomeList = new ArrayList<>();
        for (OutcomeUser o : outcomes) {
            OutcomeUserDTO outcomeUserDTO = OutcomeUserDTO.convertToDTO(o);
            outcomeList.add(outcomeUserDTO);
        }

        return outcomeList;
    }

    @Override
    public OutcomeUserDTO getOutcomeByIndex(Integer index) throws Exception {
        Member tempMember = memberRepository.findByUid(1).orElseThrow();
        OutcomeUser isOutcomeUser = outcomeUserRepository.findByIndex(index)
                .orElseThrow(() -> new IllegalArgumentException("Outcome not found with index: " + index));

        // 유저가 소유한 outcome인지 확인
        if (!isOutcomeUser.getUid().getUid().equals(tempMember.getUid())) {
            throw new AccessDeniedException("You do not have permission to delete this outcome.");
        }

        return OutcomeUserDTO.convertToDTO(isOutcomeUser);
    }

    @Override
    public OutcomeUserDTO addOutcome(OutcomeUserDTO outcomeUserDTO) throws ParseException {
        Member tempMember = memberRepository.findByUid(1).orElseThrow();
        OutcomeUser savedOutcome = outcomeUserRepository.save(OutcomeUserDTO.convertToEntity(tempMember, outcomeUserDTO));

        return OutcomeUserDTO.convertToDTO(savedOutcome);
    }

    @Transactional
    @Override
    public OutcomeUserDTO updateOutcome(OutcomeUserDTO outcomeUserDTO) throws ParseException {
        Member tempMember = memberRepository.findByUid(1).orElseThrow();

        // Outcome 조회
        OutcomeUser isOutcomeUser = outcomeUserRepository.findByIndex(outcomeUserDTO.getIndex())
                .orElseThrow(() -> new IllegalArgumentException("Outcome not found with id: " + outcomeUserDTO.getIndex()));

        // Member 조회 (Member 객체가 없을 경우 예외 처리)
        Member isMember = memberRepository.findByUid(tempMember.getUid())
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + tempMember.getUid()));

        // Outcome의 소유자가 해당 User인지 확인
        if (!isOutcomeUser.getUid().equals(isMember)) {
            throw new AccessDeniedException("You do not have permission to modify this outcome.");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        isOutcomeUser.setCategory(outcomeUserDTO.getExpCategory());
        isOutcomeUser.setDate(dateFormat.parse(outcomeUserDTO.getDate()));
        isOutcomeUser.setAmount(outcomeUserDTO.getAmount());
        isOutcomeUser.setDescript(outcomeUserDTO.getDescript());
        isOutcomeUser.setMemo(outcomeUserDTO.getMemo());

        OutcomeUser savedOutcome = outcomeUserRepository.save(isOutcomeUser);
        return OutcomeUserDTO.convertToDTO(savedOutcome);
    }

    @Transactional
    @Override
    public Integer deleteOutcomeByUidAndIndex(Integer index) {
        Member tempMember = memberRepository.findByUid(1).orElseThrow();
        OutcomeUser isOutcomeUser = outcomeUserRepository.findByIndex(index)
                .orElseThrow(() -> new IllegalArgumentException("Outcome not found with index: " + index));

        // 유저가 소유한 소비인지 확인
        if (!isOutcomeUser.getUid().getUid().equals(tempMember.getUid())) {
            throw new AccessDeniedException("You do not have permission to delete this outcome.");
        }

        outcomeUserRepository.deleteByIndex(index);  // income 삭제

        return index;
    }

}
