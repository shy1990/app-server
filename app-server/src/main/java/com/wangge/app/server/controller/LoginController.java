package com.wangge.app.server.controller;


import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.config.http.HttpRequestHandler;
import com.wangge.app.server.constant.AppInterface;
import com.wangge.app.server.util.LogUtil;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


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

  public ResponseEntity<Object> login(@RequestBody JSONObject json){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Object> repMap = new HashMap<String, Object>();
		try {
			repMap=(Map<String, Object>) httpRequestHandler.exchange(url, HttpMethod.POST, headers, json,json).getBody();
			if(repMap.get("errMsg").equals("1")){
				return  new ResponseEntity<Object>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {

			repMap.put("code", "0");
			repMap.put("msg", "数据服务器错误");
			LogUtil.info(e.getMessage());
			return new ResponseEntity<Object>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return  new ResponseEntity<Object>(repMap, HttpStatus.OK);
	}
}
