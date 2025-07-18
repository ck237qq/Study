package com.example.study.controller;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.access.UpdatePasswordReq;
import com.example.study.dto.req.access.UpdateStateReq;
import com.example.study.dto.req.access.UpdateUserInfoReq;
import com.example.study.dto.res.access.BaseInfoRes;
import com.example.study.dto.res.access.UserInfoRes;
import com.example.study.security.BaseUser;
import com.example.study.service.AccessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/access")
@Tag(name = "使用者管理")
public class AccessController {
    private final AccessService accessService;


    @Operation(summary = "更換使用者資訊", description = "更換使用者資訊")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.PUT)
    public EventMessage<String> updateUserInfo(@RequestBody UpdateUserInfoReq updateUserInfoReq) {
        return accessService.updateUserInfo(updateUserInfoReq);
    }

    @Operation(summary = "更換狀態", description = "更換狀態")
    @RequestMapping(value = "/updateState", method = RequestMethod.PUT)
    EventMessage<String> updateState(@RequestBody UpdateStateReq updateStateReq) {
        return accessService.updateState(updateStateReq);
    }

    @Operation(summary = "查詢全部使用者", description = "查詢全部使用者")
    @RequestMapping(value = "/findBaseInfo", method = RequestMethod.GET)
    public EventMessage<List<BaseInfoRes>> findBaseInfo() {
        return accessService.findBaseInfo();
    }

    @Operation(summary = "查詢指定使用者", description = "查詢指定使用者")
    @RequestMapping(value = "/getAccessById", method = RequestMethod.GET)
    public EventMessage<UserInfoRes> getAccessById(Long userInfoId) {
        return accessService.getAccessById(userInfoId);
    }

    @Operation(summary = "更換密碼", description = "更換密碼")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
    public EventMessage<String> updatePassword(
            @RequestBody UpdatePasswordReq updatePasswordReq,
            @Parameter(hidden = true) @AuthenticationPrincipal BaseUser baseUser) {
        return accessService.updatePassword(updatePasswordReq, baseUser);
    }


}
