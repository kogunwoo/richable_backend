package com.idle.kb_i_dle_backend.finance.dto;

import com.idle.kb_i_dle_backend.finance.entity.Spot;
import com.idle.kb_i_dle_backend.member.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.jdbc.Null;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotDTO {
    private Integer index;
    private String category;
    private String name;
    private Long price;
    private String addDate;

    public static SpotDTO convertToDTO(Spot spot) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return new SpotDTO(spot.getIndex(), spot.getCategory(), spot.getName(), spot.getPrice(), dateFormat.format(spot.getAddDate()));
    }

    public static Spot convertToEntity(User user, SpotDTO spotDTO) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date addDate = (spotDTO.getAddDate() != null)
                ? dateFormat.parse(spotDTO.getAddDate())
                : null;  // null 값 유지
        return new Spot(spotDTO.getIndex(), user, spotDTO.getCategory(), spotDTO.getName(), spotDTO.getPrice(), "spot", addDate, null);
    }
}
