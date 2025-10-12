package com.example.study.service;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.monthly_horoscope.MonthlyHoroscopeSaveReq;
import com.example.study.dto.res.monthly_horoscope.MonthlyHoroscopeAddRes;

import java.util.List;

public interface MonthlyHoroscopeService {

    EventMessage<List<MonthlyHoroscopeAddRes>> findMonthlyHoroscope(Long customerInfoId, Long year);

    EventMessage<String> saveMonthlyHoroscope(MonthlyHoroscopeSaveReq monthlyHoroscopeSaveReq);
}
