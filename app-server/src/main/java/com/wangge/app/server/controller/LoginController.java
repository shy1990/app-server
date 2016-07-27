package com.wangge.app.server.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.wangge.app.util.LogUtil;
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
		
		
		 return handleResult(sendRest("login", "post", talMap,url));
  }
	
	
	  /**
	   * 
	   * 
	   * handleResult:处理返回结果中包含错误信息的情况. <br/>
	   * 
	   * @author yangqc
	   * @param responseEntitu
	   * @return
	   * @since JDK 1.8
	   */
	  public static ResponseEntity<Map<String, Object>> handleResult(ResponseEntity<Map<String, Object>> responseEntitu) {
	    Map<String, Object> remap = responseEntitu.getBody();
	    if (null != remap.get("error")) {
	      if ("1".equals(remap.get("code"))) {
	        return new ResponseEntity<Map<String, Object>>(remap, HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	      return new ResponseEntity<Map<String, Object>>(remap, HttpStatus.NOT_FOUND);
	    }
	    return responseEntitu;
	  }
}

