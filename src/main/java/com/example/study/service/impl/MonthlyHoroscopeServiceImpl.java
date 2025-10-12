package com.example.study.service.impl;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.monthly_horoscope.MonthlyHoroscopeSaveReq;
import com.example.study.dto.res.monthly_horoscope.MonthlyHoroscopeAddRes;
import com.example.study.entity.MonthlyHoroscope;
import com.example.study.repository.MonthlyHoroscopeRepository;
import com.example.study.service.MonthlyHoroscopeService;
import com.example.study.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonthlyHoroscopeServiceImpl implements MonthlyHoroscopeService {

    private final MonthlyHoroscopeRepository monthlyHoroscopeRepository;

    public EventMessage<List<MonthlyHoroscopeAddRes>> findMonthlyHoroscope(Long customerInfoId, Long year) {
       List<MonthlyHoroscope> monthlyHoroscopes = monthlyHoroscopeRepository.findByCustomerInfoIdAndYear(customerInfoId, year);
       List<MonthlyHoroscopeAddRes>monthlyHoroscopeAddResList = new ArrayList<>();
       for (MonthlyHoroscope monthlyHoroscope : monthlyHoroscopes) {
           MonthlyHoroscopeAddRes monthlyHoroscopeAddRes = new MonthlyHoroscopeAddRes();
           BeanUtils.copyProperties(monthlyHoroscope, monthlyHoroscopeAddRes);
           monthlyHoroscopeAddResList.add(monthlyHoroscopeAddRes);
       }
        return CommonUtils.setDefaultEventMessage(monthlyHoroscopeAddResList);
    }


    public EventMessage<String> saveMonthlyHoroscope(MonthlyHoroscopeSaveReq monthlyHoroscopeSaveReq) {
        Long customerInfoId = monthlyHoroscopeSaveReq.getCustomerInfoId();
        Long year = monthlyHoroscopeSaveReq.getYear();
        Long month = monthlyHoroscopeSaveReq.getMonth();
        Long yaoxiangId = monthlyHoroscopeSaveReq.getYaoxiangId();

        MonthlyHoroscope monthlyHoroscope = new MonthlyHoroscope();
        if (monthlyHoroscopeSaveReq.getMonthlyHoroscopeId() == null ) {
            List<MonthlyHoroscope> res = monthlyHoroscopeRepository.findByCustomerInfoIdAndYearAndMonth(customerInfoId, year, month);
            if (!res.isEmpty()) {
                monthlyHoroscope = res.get(0);
            }
        } else {
            Optional<MonthlyHoroscope> monthlyHoroscopeOptional = monthlyHoroscopeRepository.findById(monthlyHoroscopeSaveReq.getMonthlyHoroscopeId());
            if (monthlyHoroscopeOptional.isPresent()) {
                monthlyHoroscope = monthlyHoroscopeOptional.get();
            }
        }

        monthlyHoroscope.setCustomerInfoId(customerInfoId);
        monthlyHoroscope.setYear(year);
        monthlyHoroscope.setMonth(month);
        monthlyHoroscope.setYaoxiangId(yaoxiangId);
        monthlyHoroscopeRepository.save(monthlyHoroscope);

        return CommonUtils.setDefaultEventMessage("success");
    }
}
