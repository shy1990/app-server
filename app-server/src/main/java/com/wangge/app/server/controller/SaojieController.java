package com.wangge.app.server.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.config.http.HttpRequestHandler;

@RestController
@RequestMapping(value = "/v1")
public class SaojieController {
	
  public static final String methodUrl = "saojie/";
  
  @Value("${app-interface.url}")
  private String interfaceUrl;
  
  
  @Resource
  private HttpRequestHandler httpRequestHandler;

  

  @RequestMapping(value = "/addSaojie", method = RequestMethod.POST)
  public ResponseEntity<Object> addTask(String taskName,String salesmanid,String regionid,
      String taskStart,String taskEnd,String taskCount,String taskDes,String userName) {
    Assert.notNull(userName, "userName不能为空！");
    return httpRequestHandler.exchange(interfaceUrl+"/addSaojie",HttpMethod.POST,null, taskName,salesmanid,regionid,taskStart,taskEnd,taskCount,taskDes,userName);
  }
	
	@RequestMapping(value = "/findAllSaojie", method = RequestMethod.GET)
  public ResponseEntity<Object> findAllSaojie(){
    
    return httpRequestHandler.exchange(interfaceUrl+"/findAllSaojie",HttpMethod.GET,null,null);
    
  }
	
	
}
