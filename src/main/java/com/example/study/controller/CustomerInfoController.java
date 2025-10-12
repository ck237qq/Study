package com.example.study.controller;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.customer_info.CustomerInfoAddReq;
import com.example.study.dto.req.customer_info.CustomerInfoEditReq;
import com.example.study.dto.res.customer_info.CustomerInfoRes;
import com.example.study.service.CustomerInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customerManage")
@Tag(name = "顧客管理")
public class CustomerInfoController {
    private final CustomerInfoService customerInfoService;


    @Operation(summary = "查詢全部顧客資訊", description = "查詢全部顧客資訊")
    @RequestMapping(value = "/findCustomerInfo", method = RequestMethod.GET)
    public EventMessage<List<CustomerInfoRes>> findCustomerInfo() {
        return customerInfoService.findCustomerInfo();
    }

    @Operation(summary = "新增使用者資訊", description = "新增使用者資訊")
    @RequestMapping(value = "/addCustomerInfo", method = RequestMethod.POST)
    public EventMessage<String> addCustomerInfo(@RequestBody CustomerInfoAddReq customerInfoAddReq) {
        return customerInfoService.addCustomerInfo(customerInfoAddReq);
    }

    @Operation(summary = "修改使用者資訊", description = "修改使用者資訊")
    @RequestMapping(value = "/editCustomerInfo", method = RequestMethod.PUT)
    public EventMessage<String> editCustomerInfo(@RequestBody CustomerInfoEditReq customerInfoEditReq) {
        return customerInfoService.editCustomerInfo(customerInfoEditReq);
    }
}
