package com.wangge.app.server.controller;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.wangge.app.server.vo.Apply;
import com.wangge.app.server.vo.Exam;
import com.wangge.app.server.vo.OrderPub;

@RestController
@RequestMapping(value = "/v1/mine")
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
	 * @Description: 我的收益
	 * @param @param json
	 * @param @return   
	 * @return ResponseEntity<Object>  
	 * @throws
	 * @author changjun
	 * @date 2015年11月3日
	 */
	@RequestMapping(value = "/myEarn",method = RequestMethod.POST)
	public ResponseEntity<Object> myEarn(@RequestBody JSONObject json){
		
		return new ResponseEntity<Object>(null,HttpStatus.OK);
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
	@RequestMapping(value = "/takeGoodsMoney",method = RequestMethod.POST)
	public ResponseEntity<List<OrderPub>> takeGoodsMoney(@RequestBody  JSONObject json){
		String username = json.getString("username");
		//dao查询未收款订单
		List<OrderPub> list = new ArrayList<OrderPub>();
		OrderPub order = new OrderPub();
		order.setOrderNum("123456789");
		order.setUsername(username);
		order.setAddress("大桥镇");
		order.setCreateTime(new Date());
		order.setPayState("未支付");
		list.add(order);
		return new ResponseEntity<List<OrderPub>>(list, HttpStatus.OK);
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
	@RequestMapping(value = "/noTaskMoneyRemark",method = RequestMethod.POST)
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
	@RequestMapping(value = "/applyUpdatePrice",method = RequestMethod.POST)
	public ResponseEntity<Void> applyUpdatePrice(@RequestBody  JSONObject json){
		String username = json.getString("username");
		String area = json.getString("area");
		String goodsname = json.getString("goodsname");
		String amount = json.getString("amount");
		String reason = json.getString("reason");
		//保存
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
	@RequestMapping(value = "/applyPriceState",method = RequestMethod.POST)
	public ResponseEntity<List<Apply>> applyPriceState(@RequestBody  JSONObject json){
		String username = json.getString("username");
		List<Apply> list = new ArrayList<Apply>();
		Apply apply = new Apply();
		List<String> ls = new ArrayList<String>();
		ls.add("随官屯");
		ls.add("丁官屯");
		apply.setArea(ls);
		apply.setAmount(10D);
		apply.setApplyDate(new Date());
		apply.setGoodsName("小米2A");
		apply.setReason("同行竞争");
		apply.setApplyName(username);
		list.add(apply);
		return new ResponseEntity<List<Apply>>(list, HttpStatus.OK);
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
	@RequestMapping(value = "/saveMoveMark",method = RequestMethod.POST)
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
	@RequestMapping(value = "/selMoveMarkList",method = RequestMethod.POST)
	public ResponseEntity<Object> selMoveMarkList(String username){
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
}
