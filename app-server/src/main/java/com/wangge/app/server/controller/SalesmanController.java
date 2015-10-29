package com.wangge.app.server.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.common.entity.User;

@RestController
@RequestMapping(value = "/v1/saleman")
public class SalesmanController {
	
	private static final Logger logger = Logger.getLogger(SalesmanController.class);
	@Resource
	private SalesmanRepository salesmanRepository;
	
	
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public ResponseEntity<Json> login(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("phone") String phone){
		
		Json j = new Json();
		
		try {
			Salesman user = salesmanRepository.findByUsername(username);
			
			if(!(user.getUsername().trim() != null && !"".equals(user.getUsername().trim())) || !password.equals(user.getPassword().trim())){
				j.setMsg("用户名或密码错误");
				j.setSuccess(false);
			
			}else if(!phone.equals(user.getPhone().trim())){
				j.setMsg("手机号不正确");
				j.setSuccess(false);
				
			}else{
				j.setObj(user);
				j.setMsg("登陆成功");
				j.setSuccess(true);
			}
		} catch (Exception e) {
			logger.error("login() occur error. ",e);
			e.printStackTrace();
			j.setMsg("用户名或密码错误");
			j.setSuccess(false);
		}
		
		return new ResponseEntity<Json>(j, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{username}/password",method = RequestMethod.POST)
	public ResponseEntity<Json> fixPassword(@PathVariable("username")String username,@RequestParam("password") Salesman salesman){
		Json j = new Json();
		
		try {
			 salesmanRepository.save(salesman);
			 j.setMsg("保存成功");
			 j.setSuccess(true);
		} catch (Exception e) {
			logger.error("fixPassword() occur error. ",e);
			e.printStackTrace();
			j.setMsg("保存失败");
			j.setSuccess(false);
		}
		
	   
		return new ResponseEntity<Json>(j,HttpStatus.OK);
	}

}
