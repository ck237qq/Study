package com.example.study.dto.req;

import lombok.Data;

@Data
public class RegisterUserReq {

    private String account;

    private String passwordString;

    private String name;
}
