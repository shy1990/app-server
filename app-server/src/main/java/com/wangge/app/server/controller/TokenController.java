package com.wangge.app.server.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Token;
import com.wangge.app.server.entity.User;
import com.wangge.app.server.service.TokenService;

@RestController
public class TokenController {
	@Autowired
	private TokenService tokenService;

	/**
	 * 生成token
	 * @param appid
	 * @param secret
	 * @return
	 */
	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public ResponseEntity<Token> token(@RequestParam(value = "appid", required = true) String appid,
			@RequestParam(value = "secret", required = true) String secret) {
		try {
			SecurityUtils.getSubject().login(new UsernamePasswordToken(appid, secret));
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		Token token = tokenService.generateToken((User) SecurityUtils.getSubject().getPrincipal());
		return new ResponseEntity<Token>(token, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/test")
	public String test(String test) {

		return test;
	}
}
