package com.wangge.app.server.controller;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemindController {
	/**
	 * 
	 * @Description: 新订单提醒
	 * @param @param username
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/remind/orderRemind")
	public ResponseEntity<JSONObject> orderRemind(String username){
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonStr= JSONObject.fromObject(map);
		return new ResponseEntity<JSONObject>(jsonStr, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description:消息通知
	 * @param @param username
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/remind/newsRemind")
	public ResponseEntity<JSONObject> newsRemind(String username){
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonStr= JSONObject.fromObject(map);
		return new ResponseEntity<JSONObject>(jsonStr, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description:  活动通知
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/remind/activiRemind")
	public ResponseEntity<JSONObject> activiRemind(){
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonStr= JSONObject.fromObject(map);
		return new ResponseEntity<JSONObject>(jsonStr, HttpStatus.OK);
	}
}
