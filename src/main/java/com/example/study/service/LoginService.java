package com.example.study.service;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.login.LogInReq;
import com.example.study.dto.req.access.RegisterUserReq;

public interface LoginService {
    EventMessage<String> login(LogInReq logInReq);

    EventMessage<String> signUp(RegisterUserReq registerUserReq);

}
