package com.example.study.service;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.LogInReq;
import com.example.study.dto.req.RegisterUserReq;

public interface LoginService {
    EventMessage<String> login(LogInReq logInReq);

    EventMessage<String> signUp(RegisterUserReq registerUserReq);

}
