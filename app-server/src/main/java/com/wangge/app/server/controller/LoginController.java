package com.wangge.app.server.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.config.http.HttpRequestHandler;
import com.wangge.app.server.constant.AppInterface;
import com.wangge.app.server.util.LogUtil;


@RestController
@RequestMapping(value = "/v1")
public class LoginController {
  private String url= AppInterface.url+"login";;
  @Resource
  private HttpRequestHandler httpRequestHandler;
	/**
	 * 登录 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)

  public ResponseEntity<Object> login(@RequestBody JSONObject talMap){
	HttpHeaders headers = new HttpHeaders(); 
	headers.setContentType(MediaType.APPLICATION_JSON);
	ResponseEntity<Object> object = null;
	try {
		object=httpRequestHandler.exchange(url, HttpMethod.POST, headers, talMap,new ParameterizedTypeReference<Bean>(){}, talMap);
		Map<String, ?> map=(Map<String, ?>)object.getBody();
		if(map.get("errMsg").equals("1")){
			return  new ResponseEntity<Object>(object.getBody(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} catch (Exception e) {
		Map<String, Object> repMap = new HashMap<String, Object>();
		repMap.put("code", "0");
		repMap.put("msg", "数据服务器错误");
	    LogUtil.info(e.getMessage());
		return new ResponseEntity<Object>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	return  new ResponseEntity<Object>(object.getBody(), HttpStatus.OK);
  }
}

