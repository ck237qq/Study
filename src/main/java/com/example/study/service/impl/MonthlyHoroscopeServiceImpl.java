package com.example.study.service.impl;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.monthly_horoscope.MonthlyHoroscopeReq;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonthlyHoroscopeServiceImpl implements MonthlyHoroscopeService {

    private final MonthlyHoroscopeRepository monthlyHoroscopeRepository;

    public EventMessage<List<MonthlyHoroscopeAddRes>> findMonthlyHoroscope(Long customerInfoId, Long year) {
        List<MonthlyHoroscope> monthlyHoroscopes = monthlyHoroscopeRepository.findByCustomerInfoIdAndYear(customerInfoId, year);
        List<MonthlyHoroscopeAddRes> monthlyHoroscopeAddResList = new ArrayList<>();

        Map<Long, MonthlyHoroscopeAddRes> monthlyHoroscopeAddResMap = new TreeMap<>();

        for (MonthlyHoroscope monthlyHoroscope : monthlyHoroscopes) {
            MonthlyHoroscopeAddRes monthlyHoroscopeAddRes = new MonthlyHoroscopeAddRes();
            BeanUtils.copyProperties(monthlyHoroscope, monthlyHoroscopeAddRes);
            monthlyHoroscopeAddResMap.put(monthlyHoroscope.getMonth(), monthlyHoroscopeAddRes);
        }

        for (Long i = 1L; i <= 12L; i++) {
            if (monthlyHoroscopeAddResMap.containsKey(i)) {
                monthlyHoroscopeAddResList.add(monthlyHoroscopeAddResMap.get(i));
            } else {
                MonthlyHoroscopeAddRes monthlyHoroscopeAddRes = new MonthlyHoroscopeAddRes();
                monthlyHoroscopeAddRes.setMonth(i);
                monthlyHoroscopeAddRes.setYear(year);
                monthlyHoroscopeAddRes.setCustomerInfoId(customerInfoId);
                monthlyHoroscopeAddRes.setYaoxiangId(null);
                monthlyHoroscopeAddResList.add(monthlyHoroscopeAddRes);
            }
        }

        return CommonUtils.setDefaultEventMessage(monthlyHoroscopeAddResList);
    }


    public EventMessage<String> saveMonthlyHoroscope(MonthlyHoroscopeSaveReq monthlyHoroscopeSaveReq) {
        Long customerInfoId = monthlyHoroscopeSaveReq.getCustomerInfoId();
        Long year = monthlyHoroscopeSaveReq.getYear();

        List<MonthlyHoroscope> monthlyHoroscopeList = monthlyHoroscopeRepository.findByCustomerInfoIdAndYear(customerInfoId, year);
        Map<Long, MonthlyHoroscope> monthlyHoroscopeMap = new HashMap<>();
        for (MonthlyHoroscope monthlyHoroscope : monthlyHoroscopeList) {
            monthlyHoroscopeMap.put(monthlyHoroscope.getMonth(), monthlyHoroscope);
        }


        List<MonthlyHoroscopeReq> monthlyHoroscopeReqs = monthlyHoroscopeSaveReq.getMonthlyHoroscopeReqs();
        if (monthlyHoroscopeReqs != null) {
            for (MonthlyHoroscopeReq monthlyHoroscopeReq : monthlyHoroscopeReqs) {
                Long month = monthlyHoroscopeReq.getMonth();
                if (monthlyHoroscopeMap.containsKey(month)) {
                    MonthlyHoroscope monthlyHoroscope = monthlyHoroscopeMap.get(month);
                    monthlyHoroscope.setYaoxiangId(monthlyHoroscopeReq.getYaoxiangId());
                    monthlyHoroscopeMap.put(month, monthlyHoroscope);
                } else if (month != null) {
                    MonthlyHoroscope monthlyHoroscope = new MonthlyHoroscope();
                    monthlyHoroscope.setCustomerInfoId(customerInfoId);
                    monthlyHoroscope.setYear(year);
                    monthlyHoroscope.setMonth(month);
                    monthlyHoroscopeMap.put(month, monthlyHoroscope);
                }
            }
        }

        List<MonthlyHoroscope> saveMonthlyHoroscope = new ArrayList<>();
        for (Long i = 1L; i <= 12L; i++) {
            if (monthlyHoroscopeMap.containsKey(i)) {
                saveMonthlyHoroscope.add(monthlyHoroscopeMap.get(i));
            } else {
                MonthlyHoroscope monthlyHoroscope = new MonthlyHoroscope();
                monthlyHoroscope.setCustomerInfoId(customerInfoId);
                monthlyHoroscope.setYear(year);
                monthlyHoroscope.setMonth(i);
                monthlyHoroscope.setYaoxiangId(null);
                saveMonthlyHoroscope.add(monthlyHoroscope);
            }
        }

        monthlyHoroscopeRepository.saveAll(saveMonthlyHoroscope);

        return CommonUtils.setDefaultEventMessage("success");
    }
}
