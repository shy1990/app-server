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
import com.wangge.app.server.service.SalesmanService;
import com.wangge.security.entity.User;

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
	public ResponseEntity<Json> login(@RequestBody JSONObject jsons){
		String username=jsons.getString("username");
		String password=jsons.getString("password");
		String simId=jsons.getString("simId");
		Json json = new Json();
		Salesman salesman =salesmanService.login(username,password);
		
		if(salesman !=null && !"".equals(salesman)){
			
			if((salesman.getSimId() == null || "".equals(salesman.getSimId()))){
				salesman.setSimId(simId);
				salesmanService.save(salesman);
			}else if(salesman.getSimId() != null && !"".equals(salesman.getSimId()) && simId.equals(salesman.getSimId())){
				return returnLogSucMsg(json, salesman);
		
			}else{
				  return returnLogFallMsg(json);
			}
			
				return returnLogSucMsg(json, salesman);
	
		}else {
			    return returnLogFallMsg(json);
		}
	}
	private ResponseEntity<Json> returnLogFallMsg(Json json) {
		json.setMsg("用戶名或密码错误！");
		return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
	}
	private ResponseEntity<Json> returnLogSucMsg(Json json, Salesman salesman) {
		json.setPhone(salesman.getUser().getPhone());
		json.setRegionId(salesman.getRegion().getId());
		json.setId(salesman.getId());
		json.setMsg("登陆成功！");
		return new ResponseEntity<Json>(json, HttpStatus.OK);
	}
	
}
