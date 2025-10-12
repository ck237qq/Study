package com.example.study.dto.res.monthly_horoscope;

import lombok.Data;

@Data
public class MonthlyHoroscopeAddRes {

    private Long monthlyHoroscopeId;

    private Long customerInfoId;

    private Long year;

    private Long month;

    private Long yaoxiangId;
}
