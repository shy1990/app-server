package com.wangge.app.server.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.util.RestTemplateUtil;


@RestController
@RequestMapping(value = "/v1")
public class LoginController {
	 @Value("${app-interface.url}")
	  private String url;
	/**
	 * 登录 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)

  public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> talMap){
		System.out.println(RestTemplateUtil.handleResult(RestTemplateUtil.sendRest("login", "post", talMap,url)).getStatusCode().value());
			Map<String, Object> talMapp= RestTemplateUtil.handleResult(RestTemplateUtil.sendRest("login", "post", talMap,url)).getBody();
			System.out.println(talMapp);
		 return RestTemplateUtil.handleResult(RestTemplateUtil.sendRest("login", "post", talMap,url));
  }
	
	
}

