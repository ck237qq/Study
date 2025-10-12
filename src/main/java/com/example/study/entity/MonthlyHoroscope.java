package com.example.study.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 每月運勢
 */
@Data
@Entity
public class MonthlyHoroscope {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long monthlyHoroscopeId;

    private Long customerInfoId;

    private Long year;

    private Long month;

    private Long yaoxiangId;

}
