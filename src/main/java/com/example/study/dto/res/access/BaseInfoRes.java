package com.example.study.dto.res.access;


import lombok.Data;

@Data
public class BaseInfoRes {

    private Long userInfoId;

    private String account;

    private String name;

    private long buildT;

    private long uploadT;

    private boolean state;
}
