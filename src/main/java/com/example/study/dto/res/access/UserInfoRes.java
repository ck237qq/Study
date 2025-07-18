package com.example.study.dto.res.access;


import lombok.Data;

@Data
public class UserInfoRes {

    private Long userInfoId;

    private String name;

    private String email;

    private String mobilePhone;

    private Long birthDate;

}
