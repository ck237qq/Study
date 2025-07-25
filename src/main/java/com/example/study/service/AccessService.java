package com.example.study.service;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.access.UpdatePasswordReq;
import com.example.study.dto.req.access.UpdateStateReq;
import com.example.study.dto.req.access.UpdateUserInfoReq;
import com.example.study.dto.res.access.BaseInfoRes;
import com.example.study.dto.res.access.UserInfoRes;
import com.example.study.security.BaseUser;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface AccessService {

    EventMessage<String> updateUserInfo(UpdateUserInfoReq updateUserInfoReq);

    EventMessage<List<BaseInfoRes>> findBaseInfo();

    EventMessage<UserInfoRes> getAccessById(long userInfoId);

    EventMessage<String> updateState(UpdateStateReq updateStateReq);

    EventMessage<String> updatePassword(UpdatePasswordReq updatePasswordReq, BaseUser baseUser);

    BaseUser loadUserByUsername(String userIdString) throws UsernameNotFoundException;
}
