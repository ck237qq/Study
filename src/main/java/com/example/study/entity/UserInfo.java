package com.example.study.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 使用者資訊資料庫欄位
 */
@Data
@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userInfoId;

    private String account;

    private String password;

    private long passwordUpdateTime;

    private String name;

    private String email;

    private String mobilePhone;

    private Long birthDate;

    private long buildT;

    private long uploadT;

    private boolean state;
}
