package com.example.study.dto.req;

import lombok.Data;

@Data
public class UpdatePasswordReq {

    private Long userInfoId;

    private String password;
}
