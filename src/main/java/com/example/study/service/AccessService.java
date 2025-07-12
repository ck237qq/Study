package com.example.study.service;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.LogInReq;

public interface AccessService {
    EventMessage<String> login(LogInReq logInReq);
}
