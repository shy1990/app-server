package com.wangge.app.server.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MineController {
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
	@RequestMapping(value = "/mine/examStatus")
	public ResponseEntity<JSONObject> examStatus(String username){
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
	@RequestMapping(value = "/mine/takeGoodsMoney")
	public ResponseEntity<JSONObject> takeGoodsMoney(String username){
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
	@RequestMapping(value = "/mine/noTaskMoneyRemark")
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
	@RequestMapping(value = "/mine/applyUpdatePrice")
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
	@RequestMapping(value = "/mine/applyPriceState")
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
	@RequestMapping(value = "/mine/saveMoveMark")
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
	@RequestMapping(value = "/mine/selMoveMarkList")
	public ResponseEntity<JSONObject> selMoveMarkList(String username){
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonStr= JSONObject.fromObject(map);
		return new ResponseEntity<JSONObject>(jsonStr, HttpStatus.OK);
	}
}
