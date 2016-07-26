package com.wangge.app.server.controller;


import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.constant.AppInterface;
import com.wangge.app.util.RestTemplateUtil;


@RestController
@RequestMapping(value = "/v1")
public class LoginController {
	
	  private String url = AppInterface.url + "login/";
	/**
	 * 登录 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)

  public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> talMap){
		 return RestTemplateUtil.handleResult(RestTemplateUtil.sendRest("MonthTaskSubs", "post", talMap,url));
  }
	
	
}

