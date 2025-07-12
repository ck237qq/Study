package com.example.study.controller;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.LogInReq;
import com.example.study.service.AccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "*" })
@RequiredArgsConstructor
@RequestMapping("/access")
public class AccessController {
	private final AccessService accessService;


	/**
	 * 登入
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public EventMessage<String> login(LogInReq logInReq) {
		return accessService.login(logInReq);
	}



}
