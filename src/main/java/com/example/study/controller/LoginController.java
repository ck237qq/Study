package com.example.study.controller;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.LogInReq;
import com.example.study.dto.req.RegisterUserReq;
import com.example.study.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/loginManage")
@Tag(name = "登入管理")
public class LoginController {
    private final LoginService loginService;

    @Operation(summary = "登入", description = "登入")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public EventMessage<String> login(@RequestBody LogInReq logInReq) {
        return loginService.login(logInReq);
    }

    @Operation(summary = "註冊帳號", description = "註冊帳號")
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public EventMessage<String> signUp(@RequestBody RegisterUserReq registerUserReq) {
        return loginService.signUp(registerUserReq);
    }
}
