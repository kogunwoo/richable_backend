package com.idle.kb_i_dle_backend.outcome.dto;

import com.idle.kb_i_dle_backend.income.dto.IncomeDTO;
import com.idle.kb_i_dle_backend.income.entity.Income;
import com.idle.kb_i_dle_backend.member.entity.User;
import com.idle.kb_i_dle_backend.outcome.entity.OutcomeUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OutcomeUserDTO {
    private Integer index;
    private String expCategory;
    private String date;
    private Long amount;
    private String descript;
    private String memo;

    public static OutcomeUserDTO convertToDTO(OutcomeUser outcomeUser) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return new OutcomeUserDTO(outcomeUser.getIndex(), outcomeUser.getCategory(), dateFormat.format(outcomeUser.getDate()), outcomeUser.getAmount(), outcomeUser.getDescript(), outcomeUser.getMemo());
    }

    public static OutcomeUser convertToEntity(User user, OutcomeUserDTO outcomeUserDTO) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date oDate = (outcomeUserDTO.getDate() != null)
                ? dateFormat.parse(outcomeUserDTO.getDate())
                : null;  // null 값 유지
        return new OutcomeUser(outcomeUserDTO.getIndex(), user, outcomeUserDTO.getExpCategory(), oDate, outcomeUserDTO.getAmount(), outcomeUserDTO.getDescript(), outcomeUserDTO.getMemo());
    }

}
