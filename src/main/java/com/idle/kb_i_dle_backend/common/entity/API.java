package com.idle.kb_i_dle_backend.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "api", catalog = "master")
public class API {
    @Id
    private Integer index;

    @Column(name = "open_api")
    private String openAPI;

    @Column(name = "deposite_key")
    private String depositeKey;

    @Column(name = "saving_key")
    private String savingKey;
}
