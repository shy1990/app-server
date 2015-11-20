package com.wangge.app.server.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.Message;
import com.wangge.app.server.vo.OrderPub;

@RestController
@RequestMapping({"/v1/remind"})
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
	@RequestMapping(value = "/orderList",method = RequestMethod.POST)
	public ResponseEntity<List<OrderPub>> orderList(@RequestBody  JSONObject json){
		String username = json.getString("username");
		//dao查询未收款订单
		List<OrderPub> list = new ArrayList<OrderPub>();
		OrderPub order = new OrderPub();
		order.setOrderNum("123456789");
		order.setUsername(username);
//		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		fmt.format(new Date());
		order.setCreateTime(new Date());
		list.add(order);
		return new ResponseEntity<List<OrderPub>>(list, HttpStatus.OK);
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
	@RequestMapping(value = "/selOrderDetail",method = RequestMethod.POST)
	public ResponseEntity<OrderPub> selOrderDetail(@RequestBody  JSONObject json){
		String orderNum = json.getString("orderNum");
		//根据订单号查询订单详情
		OrderPub order = new OrderPub();
		order.setCreateTime(new Date());
		List<String> ls1 = new ArrayList<String>();
		ls1.add("小米2A");
		ls1.add("红米");
		order.setGoodsName(ls1);
		List<String> ls2 =new ArrayList<String>();
		ls2.add("1234444");
		ls2.add("3212313");
		order.setGoodsNum(ls2);
		order.setOrderNum(orderNum);
		order.setStatus("已发货");
		order.setTotalCost(5500D);
		order.setUsername("章丘魅族手机专卖");
		return new ResponseEntity<OrderPub>(order, HttpStatus.OK);
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
	@RequestMapping(value = "/newsRemindList",method = RequestMethod.POST)
	public ResponseEntity<List<Message>> newsRemindList(@RequestBody  JSONObject json){
		String mobile = json.getString("mobile"); 
		//根据手机号查询消息   区分未读已读 消息在表中类型为1
		List<Message> list = new ArrayList<Message>();
		Message msg = new Message();
		msg.setContent("双11店庆");
		msg.setCreateTime(new Date());
		msg.setReceiver(mobile);
		list.add(msg);
		return new ResponseEntity<List<Message>>(list, HttpStatus.OK);
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
	@RequestMapping(value = "/newsDetail",method = RequestMethod.POST)
	public ResponseEntity<Message> newsDetail(@RequestBody  JSONObject json){
		String newsId  = json.getString("Id");
		//根据ID查消息详情
		Message msg = new Message();
		msg.setContent("双11店庆");
		msg.setCreateTime(new Date());
		return new ResponseEntity<Message>(msg, HttpStatus.OK);
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
	@RequestMapping(value = "/activiRemind",method = RequestMethod.GET)
	public ResponseEntity<Message> activiRemind(){
		//查询正在进行中的活动  不区分已读未读 活动 type=2
		Message msg = new Message();
		msg.setContent("双11店庆");
		msg.setCreateTime(new Date());
		return new ResponseEntity<Message>(msg, HttpStatus.OK);
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
	@RequestMapping(value = "/activiDetail",method = RequestMethod.POST)
	public ResponseEntity<Message> activiDetail(@RequestBody  JSONObject json){
		String id = json.getString("Id");
		//根据id查询活动详情
		Message msg = new Message();
		msg.setContent("双11店庆");
		msg.setCreateTime(new Date());
		return new ResponseEntity<Message>(msg, HttpStatus.OK);
	}
}
