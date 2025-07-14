package com.example.study.dto.req;

import lombok.Data;

@Data
public class ChangePasswordReq {

    private Long userInfoId;

    private String password;
}
