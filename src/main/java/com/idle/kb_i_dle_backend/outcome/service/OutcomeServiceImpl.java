package com.idle.kb_i_dle_backend.outcome.service;

import com.idle.kb_i_dle_backend.member.entity.User;
import com.idle.kb_i_dle_backend.member.repository.UserRepository;
import com.idle.kb_i_dle_backend.member.service.MemberService;
import com.idle.kb_i_dle_backend.outcome.dto.*;
import com.idle.kb_i_dle_backend.outcome.entity.OutcomeAverage;
import com.idle.kb_i_dle_backend.outcome.entity.OutcomeCategory;
import com.idle.kb_i_dle_backend.outcome.entity.OutcomeUser;
import com.idle.kb_i_dle_backend.outcome.repository.AverageOutcomeRepository;
import com.idle.kb_i_dle_backend.outcome.repository.CategoryRepository;
import com.idle.kb_i_dle_backend.outcome.repository.OutcomeUserRepository;
import io.swagger.models.auth.In;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import javax.swing.text.html.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice.Local;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutcomeServiceImpl implements OutcomeService {

    private final AverageOutcomeRepository averageOutcomeRepository;
    private final OutcomeUserRepository outcomeUserRepository;
    private final CategoryRepository categoryRepository;
    private final MemberService memberService;
    private final UserRepository userRepository;


    @Override
    public ResponseCategorySumListDTO findCategorySum(Integer year, Integer month) {
        List<CategorySumDTO> categorySumDTOS = outcomeUserRepository.findCategorySumByUidAndYearAndMonth(1 , year, month);
        Long sum = categorySumDTOS.stream().mapToLong(CategorySumDTO::getSum).sum();
        return new ResponseCategorySumListDTO(categorySumDTOS, sum);
    }

    @Override
    public MonthOutcomeDTO findMonthOutcome(Integer year, Integer month) {
        List<OutcomeUser> consumes = outcomeUserRepository.findAmountAllByUidAndYearAndMonth(1, year, month);
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
        MonthOutcomeDTO monthOutcomeDTO = new MonthOutcomeDTO(month,year,dailyAmount);

        return monthOutcomeDTO;
    }

    @Override
    @Transactional
    public CompareAverageCategoryOutcomeDTO compareWithAverage(int uid, Date start, Date end, String category) {
        //먼저 사용자 uid를 가져오고
        Optional<User> optionalUser = memberService.findMemberByUid(uid);
        //dto 생성
        CompareAverageCategoryOutcomeDTO compareAverageCategoryOutcomeDTO = new CompareAverageCategoryOutcomeDTO();

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            //사용자의 나이대를 가져온다.
            String ageRange =  getUserAgeRange(getUserAge(user.getBirth_year()));
            //사용자의 나이대와 카테고리의 소비 합을 조회
            List<OutcomeUser> outcomeUsers = outcomeUserRepository.findByUidAndDateBetween(user, start, end);

            //카테고리 조회
            OutcomeCategory outcomeCategory = categoryRepository.findByCategoryNameStartingWith(category);

            //date를 분기로 변경
            //사용자 나이대의 카테고리의 소비를 조회

            String quarter = getQuarter(start);

            List<OutcomeAverage> outcomeAverages = averageOutcomeRepository.findByCategoryAndQuaterAndAgeGroup(outcomeCategory.getCategoryName(), getQuarter(start), ageRange);
            String categoryName = outcomeCategory.getCategoryName();
            compareAverageCategoryOutcomeDTO.setMySum( outcomeUsers.stream().mapToLong(OutcomeUser::getAmount).sum());
            compareAverageCategoryOutcomeDTO.setAverageSum(outcomeAverages.stream().mapToLong(OutcomeAverage::getOutcome).sum());
            compareAverageCategoryOutcomeDTO.setCategory(outcomeCategory.getCategoryName());
            return compareAverageCategoryOutcomeDTO;
        }
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

    private String getQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 0-based, 0=January


        StringBuffer sb = new StringBuffer();
        return sb.append(year).append("년 ").append(month/ 4 + 1).append("분기").toString();

    }
}
