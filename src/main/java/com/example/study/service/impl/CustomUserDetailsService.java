package com.example.study.service.impl;

import com.example.study.entity.UserInfo;
import com.example.study.repository.UserInfoRepository;
import com.example.study.security.BaseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService{

    private final UserInfoRepository userInfoRepository;


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

