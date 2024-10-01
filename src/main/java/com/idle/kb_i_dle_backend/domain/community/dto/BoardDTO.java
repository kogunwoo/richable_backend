package com.idle.kb_i_dle_backend.domain.community.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {

    private Integer id;
    private String title;
    private String writer;
    private String contents;

}
