package com.wangge.app.server.controller;


import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.service.AssessService;
import com.wangge.app.server.service.ChildAccountService;
import com.wangge.app.server.service.SalaryService;
import com.wangge.app.server.service.SalesmanService;

@RestController
@RequestMapping(value = "/v1")
public class LoginController {
	
	@Resource
	private SalesmanService salesmanService;
	@Resource
	private AssessService assessService;
	@Resource
	private ChildAccountService childAccountService;
	@Resource
	private SalaryService salaryService;
	/**
	 * 登录 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)

  public String login(@RequestBody JSONObject jsons){
    
     return "";
  }
}

