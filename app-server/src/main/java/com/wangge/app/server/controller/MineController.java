package com.wangge.app.server.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<JSONObject> examStatus(@RequestBody	JSONObject json){
		String username = json.getString("username");
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonStr= JSONObject.fromObject(map);
		return new ResponseEntity<JSONObject>(jsonStr, HttpStatus.OK);
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
	public ResponseEntity<JSONObject> takeGoodsMoney(@RequestBody  JSONObject json){
		String username = json.getString("username");
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonStr= JSONObject.fromObject(map);
		return new ResponseEntity<JSONObject>(jsonStr, HttpStatus.OK);
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
	public ResponseEntity<JSONObject> noTaskMoneyRemark(String username,String orderNum,String reason){
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonStr= JSONObject.fromObject(map);
		return new ResponseEntity<JSONObject>(jsonStr, HttpStatus.OK);
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
	public ResponseEntity<JSONObject> applyUpdatePrice(String username,String town,String goodsname,Integer amount,String reason){
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonStr= JSONObject.fromObject(map);
		return new ResponseEntity<JSONObject>(jsonStr, HttpStatus.OK);
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
	public ResponseEntity<JSONObject> applyPriceState(String username){
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonStr= JSONObject.fromObject(map);
		return new ResponseEntity<JSONObject>(jsonStr, HttpStatus.OK);
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
	public ResponseEntity<JSONObject> saveMoveMark(String username,String[] potints,Date beginDate,Date endDate){
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonStr= JSONObject.fromObject(map);
		return new ResponseEntity<JSONObject>(jsonStr, HttpStatus.OK);
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
	public ResponseEntity<JSONObject> selMoveMarkList(String username){
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonStr= JSONObject.fromObject(map);
		return new ResponseEntity<JSONObject>(jsonStr, HttpStatus.OK);
	}
}
