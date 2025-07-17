package com.example.study.dto.req;

import lombok.Data;

@Data
public class UpdateStateReq {

    private Long userInfoId;

    private boolean state;
}
