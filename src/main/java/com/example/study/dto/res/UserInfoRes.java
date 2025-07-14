package com.example.study.dto.res;


import lombok.Data;

@Data
public class UserInfoRes {

    private Long userInfoId;

    private String account;

    private String name;

    private long buildT;

    private long uploadT;

    private boolean state;
}
