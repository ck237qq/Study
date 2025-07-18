package com.example.study.dto.req.access;

import lombok.Data;

@Data
public class UpdateUserInfoReq {

    private Long userInfoId;

    private String email;

    private String mobilePhone;

    private String birthDateString;
}
