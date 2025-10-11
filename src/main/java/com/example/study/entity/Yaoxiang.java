package com.example.study.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * 爻象
 */
@Data
@Entity
public class Yaoxiang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long yaoxiangId;

    private Long hexagramId;

    private Long hexagramYaoxiangId;

    private int index;

    private String remark;

    private String state;

}
