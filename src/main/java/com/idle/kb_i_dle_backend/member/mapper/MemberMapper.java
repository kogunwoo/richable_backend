package com.idle.kb_i_dle_backend.member.mapper;

import com.idle.kb_i_dle_backend.member.dto.MemberDTO;
import com.idle.kb_i_dle_backend.member.dto.MemberJoinDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {
    @Select("SELECT COUNT(*) > 0 FROM user_info.user_info WHERE id = #{id}")
    boolean checkDupl(String id);

    @Select("SELECT * FROM user_info.user_info WHERE id = #{id}")
    MemberDTO getMember(String id);

    @Select("SELECT id, nickname, gender, email, birth_year FROM user_info.user_info WHERE id = #{id}")
    MemberJoinDTO getMemberJoin(String id);

    @Select("SELECT * FROM user_info.user_info WHERE id = #{id}")
    MemberDTO findById(String id);
}
