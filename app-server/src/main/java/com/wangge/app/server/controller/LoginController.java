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
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.common.entity.User;

@RestController
@RequestMapping(value = "/v1")
public class LoginController {
	
	private static final Logger logger = Logger.getLogger(LoginController.class);
	
	@Resource
	private SalesmanRepository salesmanRepository;
	/**
	 * 登录 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public ResponseEntity<Json> login(@RequestBody JSONObject json){
		String username=json.getString("username");
		String password=json.getString("password");
		String phone=json.getString("phone");
		
	// User salesman = salesmanRepository.findByUsernameAndPasswordAndPhone(username,password,phone);
		
	 Json json1 = new Json();
	 Salesman salesman = salesmanRepository.findByUsernameAndPassword(username,password);
		try {
			
			
			if((salesman == null || "".equals(salesman))){
				json1.setMsg("用户名或密码错误！");
				return new ResponseEntity<Json>(json1, HttpStatus.UNAUTHORIZED);
			
			}else if(!phone.equals(salesman.getPhone().trim())){
				json1.setMsg("手机号不正确");
				return new ResponseEntity<Json>(json1, HttpStatus.UNAUTHORIZED);
				
			}else{
				json1.setObj(salesman);
				json1.setMsg("登陆成功");
				return new ResponseEntity<Json>(json1, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("login() occur error. ",e);
			e.printStackTrace();
			json1.setMsg("登陆异常!");
			return new ResponseEntity<Json>(json1, HttpStatus.UNAUTHORIZED);
		}
	 
	 
	 
	 
		
		/*if (salesman == null || "".equals(salesman)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<Salesman>((Salesman) salesman, HttpStatus.OK);*/
	}
	

}
