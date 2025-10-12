package com.example.study.dto.req.monthly_horoscope;

import lombok.Data;

@Data
public class MonthlyHoroscopeSaveReq {

    private Long monthlyHoroscopeId;

    private Long customerInfoId;

    private Long year;

    private Long month;

    private Long yaoxiangId;
}
