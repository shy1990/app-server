package com.wangge.app.server.controller;


import java.util.Map;

import javax.annotation.Resource;

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
import com.wangge.app.util.RestTemplateUtil;


@RestController
@RequestMapping(value = "/v1")
public class LoginController {
  private String url= AppInterface.url;;
  @Resource
  private HttpRequestHandler httpRequestHandler;
	/**
	 * 登录 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)

  public ResponseEntity<Map<String, Object>> login(@RequestBody JSONObject talMap){
	HttpHeaders headers = new HttpHeaders(); 
	headers.setContentType(MediaType.APPLICATION_JSON);
	httpRequestHandler.exchange(url+"login", HttpMethod.POST, headers, talMap, "");
	return handleResult(RestTemplateUtil.sendRest("login", "post", talMap,url));
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

