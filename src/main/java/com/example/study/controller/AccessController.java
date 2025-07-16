package com.example.study.controller;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.ChangePasswordReq;
import com.example.study.dto.res.UserInfoRes;
import com.example.study.security.BaseUser;
import com.example.study.service.AccessService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "查詢全部使用者", description = "查詢全部使用者")
    @RequestMapping(value = "/findAccess", method = RequestMethod.GET)
    public EventMessage<List<UserInfoRes>> findAccess() {
        return accessService.findAccess();
    }

    @Operation(summary = "更換密碼", description = "更換密碼")
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public EventMessage<String> changePassword(@RequestBody ChangePasswordReq changePasswordReq, @AuthenticationPrincipal BaseUser baseUser) {
        return accessService.changePassword(changePasswordReq, baseUser);
    }


}
