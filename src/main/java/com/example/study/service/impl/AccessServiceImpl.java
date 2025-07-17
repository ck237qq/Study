package com.example.study.service.impl;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto._event.ResultCode;
import com.example.study.dto.req.UpdatePasswordReq;
import com.example.study.dto.req.UpdateStateReq;
import com.example.study.dto.req.UpdateUserInfoReq;
import com.example.study.dto.res.BaseInfoRes;
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

import java.time.LocalDate;
import java.time.ZoneId;
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

    public EventMessage<String> updateUserInfo(UpdateUserInfoReq updateUserInfoReq) {
        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(updateUserInfoReq.getUserInfoId());
        if (userInfoOptional.isEmpty()) {
            return CommonUtils.setExceptionEventMessage(ResultCode.ERR_2001.getCode(), ResultCode.ERR_2001.getDesc(), null);
        }
        UserInfo userInfo = userInfoOptional.get();
        BeanUtils.copyProperties(updateUserInfoReq, userInfo);
        String birthDateString = updateUserInfoReq.getBirthDateString();
        LocalDate localDate = LocalDate.parse(birthDateString);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        userInfo.setBirthDate(date.getTime());
        userInfoRepository.save(userInfo);

        return CommonUtils.setDefaultEventMessage("success");
    }

    public EventMessage<String> updateState(UpdateStateReq updateStateReq) {
        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(updateStateReq.getUserInfoId());
        if (userInfoOptional.isEmpty()) {
            return CommonUtils.setExceptionEventMessage(ResultCode.ERR_2001.getCode(), ResultCode.ERR_2001.getDesc(), null);
        }

        UserInfo userInfo = userInfoOptional.get();
        userInfo.setState(updateStateReq.isState());
        userInfoRepository.save(userInfo);

        return CommonUtils.setDefaultEventMessage("success");
    }


    public EventMessage<List<BaseInfoRes>> findBaseInfo() {
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        List<BaseInfoRes> baseInfoResList = new ArrayList<>();
        for (UserInfo userInfo : userInfoList) {
            BaseInfoRes baseInfoRes = new BaseInfoRes();
            BeanUtils.copyProperties(userInfo, baseInfoRes);
            baseInfoResList.add(baseInfoRes);
        }
        return CommonUtils.setDefaultEventMessage(baseInfoResList);
    }

    public EventMessage<UserInfoRes> getAccessById(long userInfoId) {
        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(userInfoId);
        if (userInfoOptional.isEmpty()) {
            return CommonUtils.setExceptionEventMessage(ResultCode.ERR_2001.getCode(), ResultCode.ERR_2001.getDesc(), null);
        }
        UserInfo userInfo = userInfoOptional.get();
        UserInfoRes userInfoRes = new UserInfoRes();
        BeanUtils.copyProperties(userInfo, userInfoRes);

        return CommonUtils.setDefaultEventMessage(userInfoRes);
    }


    public EventMessage<String> updatePassword(UpdatePasswordReq updatePasswordReq, BaseUser baseUser) {
        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(updatePasswordReq.getUserInfoId());
        if (userInfoOptional.isEmpty()) {
            return CommonUtils.setExceptionEventMessage(ResultCode.ERR_2001.getCode(), ResultCode.ERR_2001.getDesc(), null);
        }

        UserInfo userInfo = userInfoOptional.get();
        // 非使用者本人不允許修改密碼
        if (!Objects.equals(userInfo.getUserInfoId(), baseUser.getUserInfoId())) {
            return CommonUtils.setExceptionEventMessage(ResultCode.ERR_2001.getCode(), ResultCode.ERR_2001.getDesc(), null);
        }

        long nowTime = new Date().getTime();
        String hashPassword = HashUtils.hashPassword(updatePasswordReq.getPassword() + "_" + nowTime);
        userInfo.setPassword(hashPassword);
        userInfo.setPasswordUpdateTime(nowTime);
        userInfoRepository.save(userInfo);

        return CommonUtils.setDefaultEventMessage("success");
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
