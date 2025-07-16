package com.example.study.service.impl;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto._event.ResultCode;
import com.example.study.dto.req.ChangePasswordReq;
import com.example.study.dto.res.UserInfoRes;
import com.example.study.entity.UserInfo;
import com.example.study.repository.UserInfoRepository;
import com.example.study.security.BaseUser;
import com.example.study.service.AccessService;
import com.example.study.utils.CommonUtils;
import com.example.study.utils.HashUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class AccessServiceImpl implements AccessService {
    private final UserInfoRepository userInfoRepository;

    public AccessServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;

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

    public EventMessage<List<UserInfoRes>> findAccess () {
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        List<UserInfoRes> userInfoResList = new ArrayList<>();
        for (UserInfo userInfo : userInfoList) {
            UserInfoRes userInfoRes = new UserInfoRes();
            BeanUtils.copyProperties(userInfo, userInfoRes);
            userInfoResList.add(userInfoRes);
        }
        return CommonUtils.setDefaultEventMessage(userInfoResList);
    }

    public EventMessage<String> changePassword(ChangePasswordReq changePasswordReq,  BaseUser baseUser) {
        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(changePasswordReq.getUserInfoId());
        if (userInfoOptional.isEmpty()) {
            String code = ResultCode.ERR_2001.getCode();
            String msg = ResultCode.ERR_2001.getDesc();
            return CommonUtils.setExceptionEventMessage(code, msg, null);
        }

        UserInfo userInfo = userInfoOptional.get();
        // 非使用者本人不允許修改密碼
        if (!Objects.equals(userInfo.getUserInfoId(), baseUser.getUserInfoId())) {
            String code = ResultCode.ERR_2001.getCode();
            String msg = ResultCode.ERR_2001.getDesc();
            return CommonUtils.setExceptionEventMessage(code, msg, null);
        }

        long nowTime = new Date().getTime();
        String hashPassword = HashUtils.hashPassword(changePasswordReq.getPassword() + "_" + nowTime);
        userInfo.setPassword(hashPassword);
        userInfo.setPasswordUpdateTime(nowTime);
        userInfoRepository.save(userInfo);

        String msg = ResultCode.ERR_0000.getDesc();
        return CommonUtils.setDefaultEventMessage(msg);
    }

    public BaseUser loadUserByUsername(String userIdString) throws UsernameNotFoundException {
        long userId = Long.parseLong(userIdString);
        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(userId);

        if (userInfoOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with userId : " + userId);
        }

        UserInfo userInfo = userInfoOptional.get();
        BaseUser baseUser = new BaseUser();
        BeanUtils.copyProperties(userInfo, baseUser);

        return baseUser;
    }
}
