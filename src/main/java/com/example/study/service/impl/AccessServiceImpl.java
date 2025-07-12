package com.example.study.service.impl;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto._event.ResultCode;
import com.example.study.dto.req.LogInReq;
import com.example.study.entity.UserInfo;
import com.example.study.repository.UserInfoRepository;
import com.example.study.service.AccessService;
import com.example.study.utils.CommonUtils;
import com.example.study.utils.HashUtils;
import com.example.study.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class AccessServiceImpl implements AccessService {
    private final UserInfoRepository userInfoRepository;
    private final JwtUtil jwtUtil;

    public AccessServiceImpl(UserInfoRepository userInfoRepository, JwtUtil jwtUtil) {
        this.userInfoRepository = userInfoRepository;
        this.jwtUtil = jwtUtil;

        // 自動生成管理員帳號
        if (userInfoRepository.findByAccount("admin") == null) {
            long nowTime = new Date().getTime();
            UserInfo userInfo = new UserInfo();
            userInfo.setAccount("admin");
            String hashPassword = HashUtils.hashPassword("admin_" + nowTime);
            userInfo.setPassword(hashPassword);
            userInfo.setPasswordUpdateTime(nowTime);
            userInfo.setName("管理員帳號");
            userInfo.setBuildT(nowTime);
            userInfo.setUploadT(nowTime);
            userInfo.setState(true);
            userInfoRepository.save(userInfo);
        }
    }


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

    private String genJwtToken(String userIdString) {
        return jwtUtil.generateToken(userIdString, "study");
    }
}
