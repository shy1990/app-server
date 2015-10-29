package com.wangge.app.server.controller;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/remind"})
public class RemindController {
	
	private static final Logger logger = LoggerFactory.getLogger(RemindController.class);
	/**
	 * 
	 * @Description: 订单列表
	 * @param @param username
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/orderList")
	public ResponseEntity<JSONObject> orderList(String username){
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonStr= JSONObject.fromObject(map);
		return new ResponseEntity<JSONObject>(jsonStr, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 订单详情
	 * @param @param ordernum
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月29日
	 */
	@RequestMapping(value = "/selOrderDetail")
	public ResponseEntity<JSONObject> selOrderDetail(String ordernum){
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
	@RequestMapping(value = "/newsRemindList")
	public ResponseEntity<JSONObject> newsRemindList(String username){
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonStr= JSONObject.fromObject(map);
		return new ResponseEntity<JSONObject>(jsonStr, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 消息详情
	 * @param @param newsId
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月29日
	 */
	@RequestMapping(value = "/newsDetail")
	public ResponseEntity<JSONObject> newsDetail(String newsId){
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
	@RequestMapping(value = "/activiRemind")
	public ResponseEntity<JSONObject> activiRemind(){
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonStr= JSONObject.fromObject(map);
		return new ResponseEntity<JSONObject>(jsonStr, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 活动详情
	 * @param @param id
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月29日
	 */
	@RequestMapping(value = "/activiDetail")
	public ResponseEntity<JSONObject> activiDetail(String id){
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonStr= JSONObject.fromObject(map);
		return new ResponseEntity<JSONObject>(jsonStr, HttpStatus.OK);
	}
}
