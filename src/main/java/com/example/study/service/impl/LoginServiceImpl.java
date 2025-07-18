package com.example.study.service.impl;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto._event.ResultCode;
import com.example.study.dto.req.login.LogInReq;
import com.example.study.dto.req.access.RegisterUserReq;
import com.example.study.entity.UserInfo;
import com.example.study.repository.UserInfoRepository;
import com.example.study.service.LoginService;
import com.example.study.utils.CommonUtils;
import com.example.study.utils.HashUtils;
import com.example.study.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserInfoRepository userInfoRepository;
    private final JwtUtil jwtUtil;


    public EventMessage<String> login(LogInReq logInReq) {
        UserInfo userInfo = userInfoRepository.findByAccount(logInReq.getAccount());
        if (userInfo == null) {
            String code = ResultCode.ERR_2002.getCode();
            String msg = ResultCode.ERR_2002.getDesc();
            return CommonUtils.setExceptionEventMessage(code, msg, null);
        }
        String hashedPassword = userInfo.getPassword();
        String rawPassword = logInReq.getPassword() + "_" + userInfo.getPasswordUpdateTime();

        if (!HashUtils.verifyPassword(rawPassword, hashedPassword)) {
            String code = ResultCode.ERR_2002.getCode();
            String msg = ResultCode.ERR_2002.getDesc();
            return CommonUtils.setExceptionEventMessage(code, msg, null);
        }
        return CommonUtils.setDefaultEventMessage(genJwtToken(userInfo.getUserInfoId().toString()));
    }

    public EventMessage<String> signUp(RegisterUserReq registerUserReq) {
        if (userInfoRepository.findByAccount(registerUserReq.getAccount()) != null) {
            String code = ResultCode.ERR_2001.getCode();
            String msg = ResultCode.ERR_2001.getDesc();
            return CommonUtils.setExceptionEventMessage(code, msg, null);
        }

        long nowTime = new Date().getTime();
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(registerUserReq, userInfo);
        String hashPassword = HashUtils.hashPassword(registerUserReq.getPasswordString() + "_" + nowTime);
        userInfo.setPassword(hashPassword);
        userInfo.setPasswordUpdateTime(nowTime);
        userInfo.setBuildT(nowTime);
        userInfo.setUploadT(nowTime);
        userInfo.setState(false);
        userInfoRepository.save(userInfo);

        return CommonUtils.setDefaultEventMessage("");
    }

    private String genJwtToken(String userIdString) {
        return jwtUtil.generateToken(userIdString, "study");
    }
}
