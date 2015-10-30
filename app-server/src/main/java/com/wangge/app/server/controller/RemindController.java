package com.wangge.app.server.controller;


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
	public ResponseEntity<Object> orderList(String username){
		return new ResponseEntity<Object>(null, HttpStatus.OK);
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
	public ResponseEntity<Object> selOrderDetail(String ordernum){
		return new ResponseEntity<Object>(null, HttpStatus.OK);
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
	public ResponseEntity<Object> newsRemindList(String username){
		return new ResponseEntity<Object>(null, HttpStatus.OK);
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
	public ResponseEntity<Object> newsDetail(String newsId){
		return new ResponseEntity<Object>(null, HttpStatus.OK);
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
	public ResponseEntity<Object> activiRemind(){
		return new ResponseEntity<Object>(null, HttpStatus.OK);
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
	public ResponseEntity<Object> activiDetail(String id){
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
}
