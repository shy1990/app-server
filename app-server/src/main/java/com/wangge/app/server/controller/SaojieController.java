package com.wangge.app.server.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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
	
  
  @Value("${app-interface.url}")
  private String interfaceUrl;
  
  
  @Resource
  private HttpRequestHandler httpRequestHandler;

  

  @ApiOperation(value="添加扫接任务",notes="添加扫街")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "taskName", value = "taskName", required = true, dataType = "String"),
      @ApiImplicitParam(name = "salesmanid", value = "salesmanid", required = true, dataType = "String"),
      @ApiImplicitParam(name="regionid",value="regionid",required=true,dataType="String"), @ApiImplicitParam(name="taskStart",value="taskStart",required=true,dataType="String"), @ApiImplicitParam(name="taskEnd",value="taskEnd",required=true,dataType="String"),
      @ApiImplicitParam(name="taskCount",value="taskCount",required=true,dataType="Stirng"), @ApiImplicitParam(name="taskDes",value="taskDes",required=true,dataType="String"), @ApiImplicitParam(name="userName",value="userName",required=true,dataType="String") })
  @RequestMapping(value = "/addSaojie", method = RequestMethod.POST)
  public JSONObject addTask(String taskName,String salesmanid,String regionid,
      String taskStart,String taskEnd,String taskCount,String taskDes,String userName) {
    JSONObject json = new JSONObject();
    json.put("taskName", taskName);
    json.put("salesmanid", salesmanid);
    json.put("regionid", regionid);
    json.put("taskStart", taskStart);
    json.put("taskEnd", taskEnd);
    json.put("taskCount", taskCount);
    json.put("taskDes", taskDes);
    json.put("userName", userName);
    Assert.notNull(userName, "userName不能为空！");
    return httpRequestHandler.exchange(interfaceUrl+"/addSaojie", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {});
  }
  
	@ApiOperation(value="获取扫街",notes="获取扫街")
  @ApiImplicitParam(name="userName",value="userName",required=true,dataType="String")
	@RequestMapping(value = "/findAllSaojie", method = RequestMethod.GET)
  public JSONObject findAllSaojie(String userName){
    
    return httpRequestHandler.exchange(interfaceUrl+"/findAllSaojie", HttpMethod.GET, null, userName,new ParameterizedTypeReference<JSONObject>() {});
    
  }
	
	
}
