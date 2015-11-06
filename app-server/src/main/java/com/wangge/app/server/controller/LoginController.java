package com.wangge.app.server.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.service.SalesmanService;

@RestController
@RequestMapping(value = "/v1")
public class LoginController {
	
	private static final Logger logger = Logger.getLogger(LoginController.class);
	
	@Resource
	private SalesmanService salesmanService;
	/**
	 * 登录 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public ResponseEntity<Salesman> login(@RequestBody JSONObject json){
		String username=json.getString("username");
		String password=json.getString("password");
		String phone=json.getString("phone");
	 
		Salesman salesman=salesmanService.login(username,password,phone);
		if (salesman == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<Salesman>(salesman, HttpStatus.OK);
	}
	
}
