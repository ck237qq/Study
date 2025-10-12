package com.example.study.dto.req.customer_info;

import lombok.Data;

@Data
public class CustomerInfoEditReq {

    private Long customerInfoId;

    private String birthdayDateString;

    private String customerName;
}
