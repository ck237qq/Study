package com.example.study.service;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.customer_info.CustomerInfoAddReq;
import com.example.study.dto.req.customer_info.CustomerInfoEditReq;
import com.example.study.dto.res.customer_info.CustomerInfoRes;

import java.util.List;

public interface CustomerInfoService {

    EventMessage<List<CustomerInfoRes>> findCustomerInfo();

    EventMessage<String> addCustomerInfo(CustomerInfoAddReq customerInfoAddReq);

    EventMessage<String> editCustomerInfo(CustomerInfoEditReq customerInfoEditReq);
}
