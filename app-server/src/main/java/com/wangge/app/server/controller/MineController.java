package com.wangge.app.server.controller;


import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.Exam;

@RestController
@RequestMapping(value = "/mine")
public class MineController {
	
	private static final Logger logger = LoggerFactory.getLogger(MineController.class);
	
	/**
	 * 
	 * @Description: 考核状态
	 * @param @param username
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/examStatus",method = RequestMethod.POST)
	public ResponseEntity<Exam> examStatus(@RequestBody	JSONObject json){
		String username = json.getString("username");
		Exam ex = new Exam();
		ex.setStage("第一阶段");
		ex.setBeginDate(new Date());
		ex.setDoneNum("7");
		ex.setEndDate(new Date());
		ex.setKpiNum("10");
		double rate = 7D / 10D;
		DecimalFormat df = new DecimalFormat("0.00%");   
		ex.setRate(df.format(rate));
		ex.setRemark("考核审核标准：二次提货客户达到10家即为达标");
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("郭屯镇", 5);
		map.put("随官屯", 2);
		ex.setMap(map);
		
		return new ResponseEntity<Exam>(ex, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description:  任务-收货款
	 * @param @param username
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/takeGoodsMoney")
	public ResponseEntity<Object> takeGoodsMoney(@RequestBody  JSONObject json){
		String username = json.getString("username");
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 未收款报备
	 * @param @param username
	 * @param @param orderNum
	 * @param @param reason
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/noTaskMoneyRemark")
	public ResponseEntity<Void> noTaskMoneyRemark(@RequestBody  JSONObject json){
		String username = json.getString("username");
		String orderNum = json.getString("orderNum");
		String reason = json.getString("reason");
		System.out.println(username+":"+orderNum+":"+reason);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 申请调价
	 * @param @param username
	 * @param @param town
	 * @param @param goodsname
	 * @param @param amount
	 * @param @param reason
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/applyUpdatePrice")
	public ResponseEntity<Void> applyUpdatePrice(@RequestBody  JSONObject json){
		String username = json.getString("username");
		String town = json.getString("town");
		String goodsname = json.getString("goodsname");
		String amount = json.getString("amount");
		String reason = json.getString("reason");
		return new ResponseEntity<Void>( HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 申请调价列表状态
	 * @param @param username
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/applyPriceState")
	public ResponseEntity<Object> applyPriceState(String username){
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 我的足记
	 * @param @param username
	 * @param @param potints
	 * @param @param beginDate
	 * @param @param endDate
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/saveMoveMark")
	public ResponseEntity<Object> saveMoveMark(String username,String[] potints,Date beginDate,Date endDate){
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description:查看我的足迹列表
	 * @param @param username
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/selMoveMarkList")
	public ResponseEntity<Object> selMoveMarkList(String username){
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
}
