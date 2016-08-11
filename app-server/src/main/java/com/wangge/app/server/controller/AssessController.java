package com.wangge.app.server.controller;

import com.wangge.app.server.config.http.HttpRequestHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/v1/assess")
public class AssessController {
	@Resource
	private HttpRequestHandler httpRequestHandler;
	@Value("${app-interface.url}")
	private String url;

	@RequestMapping(value = "/findAllAssess", method = RequestMethod.POST)
	public ResponseEntity<Object> findAllTask(String userid){
		return httpRequestHandler.exchange(url + "/assess/findAllAssess", HttpMethod.POST,null,userid,null);
	}
	
	@RequestMapping(value = "/addAssess", method = RequestMethod.POST)
	public ResponseEntity<Object> addAssess(String salesmanid,String assessStage,String assessActivenum,String assessOrdernum,String assessCycle,String accessTime,String regionid,String regionzh,String taskid){
		return httpRequestHandler.exchange(url + "/assess/addAssess", HttpMethod.POST,null,salesmanid,assessStage,assessActivenum,assessOrdernum,assessCycle,accessTime,regionid,regionzh,taskid,null);
	}
	
	
	
	
}
