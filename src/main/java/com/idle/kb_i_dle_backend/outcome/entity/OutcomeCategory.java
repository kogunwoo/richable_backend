package com.idle.kb_i_dle_backend.outcome.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "outcome_expenditure_category", catalog = "outcome")
@Getter
public class OutcomeCategory {

    @Id
    private Integer index;

    @Column(name = "category_name")
    private String categoryName;

}
