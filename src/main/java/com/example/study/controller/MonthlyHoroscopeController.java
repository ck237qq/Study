package com.example.study.controller;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.monthly_horoscope.MonthlyHoroscopeSaveReq;
import com.example.study.dto.res.monthly_horoscope.MonthlyHoroscopeAddRes;
import com.example.study.service.MonthlyHoroscopeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/monthlyHoroscopeManage")
@Tag(name = "顧客流年管理")
@CrossOrigin(origins = { "*" })
public class MonthlyHoroscopeController {
    private final MonthlyHoroscopeService monthlyHoroscopeService;


    @Operation(summary = "查詢指定使用者當年流年", description = "查詢指定使用者當年流年")
    @RequestMapping(value = "/findMonthlyHoroscope", method = RequestMethod.GET)
    public EventMessage<List<MonthlyHoroscopeAddRes>> findMonthlyHoroscope(Long customerInfoId, Long year) {
        return monthlyHoroscopeService.findMonthlyHoroscope(customerInfoId, year);
    }

    @Operation(summary = "更新指定使用者當月運勢", description = "更新指定使用者當月運勢")
    @RequestMapping(value = "/saveMonthlyHoroscope", method = RequestMethod.POST)
    public EventMessage<String> saveMonthlyHoroscope(@RequestBody MonthlyHoroscopeSaveReq monthlyHoroscopeSaveReq) {
        return monthlyHoroscopeService.saveMonthlyHoroscope(monthlyHoroscopeSaveReq);
    }

}
