package com.example.study.service;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.ChangePasswordReq;
import com.example.study.dto.res.UserInfoRes;
import com.example.study.security.BaseUser;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface AccessService {

    EventMessage<List<UserInfoRes>> findAccess ();

    EventMessage<String> changePassword(ChangePasswordReq changePasswordReq,  BaseUser baseUser);

    BaseUser loadUserByUsername(String userIdString) throws UsernameNotFoundException;
}
