package com.example.study.dto.req.access;

import lombok.Data;

@Data
public class UpdateStateReq {

    private Long userInfoId;

    private boolean state;
}
