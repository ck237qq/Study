package com.example.study.service.impl;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.customer_info.CustomerInfoAddReq;
import com.example.study.dto.req.customer_info.CustomerInfoEditReq;
import com.example.study.dto.res.customer_info.CustomerInfoRes;
import com.example.study.entity.CustomerInfo;
import com.example.study.repository.CustomerInfoRepository;
import com.example.study.service.CustomerInfoService;
import com.example.study.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerInfoServiceServiceImpl implements CustomerInfoService {

    private final CustomerInfoRepository customerInfoRepository;


    // 使用 uuuu 而不是 yyyy，並開啟 STRICT 確保日期有效
    private static final DateTimeFormatter STRICT_YMDHM =
            DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);


    public EventMessage<List<CustomerInfoRes>> findCustomerInfo() {
        List<CustomerInfo> customerInfos = customerInfoRepository.findAll();
        List<CustomerInfoRes> customerInfoResList = new ArrayList<>();
        for (CustomerInfo customerInfo : customerInfos) {
            CustomerInfoRes customerInfoRes = new CustomerInfoRes();
            BeanUtils.copyProperties(customerInfo, customerInfoRes);

            LocalDateTime birthdayDate = customerInfo.getBirthdayDate();
            String birthdayDateString = birthdayDate.format(STRICT_YMDHM);    // 2025/10/12 14:30
            customerInfoRes.setBirthdayDateString(birthdayDateString);

            customerInfoResList.add(customerInfoRes);
        }
        return CommonUtils.setDefaultEventMessage(customerInfoResList);
    }


    public EventMessage<String> addCustomerInfo(CustomerInfoAddReq customerInfoAddReq) {
        String birthdayDateString = customerInfoAddReq.getBirthdayDateString();
        if (isNotValidYMDHM(birthdayDateString)) {
            return CommonUtils.setExceptionEventMessage("2001", "格式或日期時間無效，期望：yyyy-MM-dd HH:mm", null);
        }

        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtils.copyProperties(customerInfoAddReq, customerInfo);

        LocalDateTime birthdayDate = LocalDateTime.parse(birthdayDateString, STRICT_YMDHM);
        customerInfo.setBirthdayDate(birthdayDate);

        customerInfoRepository.save(customerInfo);

        return CommonUtils.setDefaultEventMessage("success");
    }

    public EventMessage<String> editCustomerInfo(CustomerInfoEditReq customerInfoEditReq) {
        Long customerInfoId = customerInfoEditReq.getCustomerInfoId();
        Optional<CustomerInfo> customerInfoOptional = customerInfoRepository.findById(customerInfoId);

        if (customerInfoOptional.isEmpty()) {
            return CommonUtils.setExceptionEventMessage("2001", "customerInfoId is not exist", null);
        }

        String birthdayDateString = customerInfoEditReq.getBirthdayDateString();
        if (isNotValidYMDHM(birthdayDateString)) {
            CommonUtils.setExceptionEventMessage("2001", "格式或日期時間無效，期望：yyyy-MM-dd HH:mm", null);
        }

        CustomerInfo customerInfo = customerInfoOptional.get();
        BeanUtils.copyProperties(customerInfoEditReq, customerInfo);
        LocalDateTime birthdayDate = LocalDateTime.parse(birthdayDateString, STRICT_YMDHM);
        customerInfo.setBirthdayDate(birthdayDate);

        customerInfoRepository.save(customerInfo);

        return CommonUtils.setDefaultEventMessage("success");
    }



    public static boolean isNotValidYMDHM(String text) {
        if (text == null) return true;
        try {
            LocalDateTime.parse(text, STRICT_YMDHM);
            return false;
        } catch (DateTimeParseException e) {
            return true;
        }
    }



}
