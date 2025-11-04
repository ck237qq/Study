package com.example.study.dto.req.monthly_horoscope;

import lombok.Data;

import java.util.List;

@Data
public class MonthlyHoroscopeSaveReq {

    private Long customerInfoId;

    private Long year;

    private List<MonthlyHoroscopeReq> monthlyHoroscopeReqs;
}
