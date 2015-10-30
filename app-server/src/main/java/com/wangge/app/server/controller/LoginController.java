package com.wangge.app.server.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.Salesman;

@RestController
@RequestMapping(value = "/v1")
public class LoginController {
	
	private static final Logger logger = Logger.getLogger(LoginController.class);
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
		
		if (!"yewuzhangsan".equals(username)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Salesman s=new Salesman();
		s.setId("1");
		s.setNickname("业务张三");
		s.setPhone("1888888888");
		s.setUsername("yewuzhangsan");
		return new ResponseEntity<Salesman>(s, HttpStatus.OK);
	}
	

}
