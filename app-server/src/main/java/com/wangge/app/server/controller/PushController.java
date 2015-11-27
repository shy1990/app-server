package com.wangge.app.server.controller;
import java.util.Date;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Message;
import com.wangge.app.server.entity.Message.MessageType;
import com.wangge.app.server.entity.Message.SendChannel;
import com.wangge.app.server.jpush.client.JpushClient;
import com.wangge.app.server.service.MessageService;

@RestController
@RequestMapping({ "/v1/push"})
public class PushController {
	
	private static final Logger logger = LoggerFactory.getLogger(PushController.class);
	
	@Resource
	private MessageService mr;
	/**
	 * 
	 * @Description: 新订单推送
	 * @param @param msg
	 * @param @return   
	 * @return boolean  
	 * @throws
	 * @author changjun
	 * @date 2015年11月5日
	 */
	@RequestMapping(value = { "/pushNewOrder"},method = RequestMethod.POST)
	public boolean pushNewOrder(String msg){
		/**
		 * 【20151029164718792】山东省潍坊市青州市潍坊市青州市|弥河镇慧霞手机店下单成功，
		 * 订单商品:标准版Samsung/三星 三星SM-G5308W*1台,标准版Xiaomi/小米 红米 红米note2*2台
		 *{"orderNum":"222222222222222","mobiles":"1561069 62989","amount":"10.0","username":"天桥魅族店"}
		 */
		JSONObject json = new JSONObject(msg);
		String send = json.getString("username")+",总金额:"+json.getString("amount")+",订单号:"+json.getString("orderNum");
		String str = "";
		try {
			str = JpushClient.sendOrder("下单通知", send,json.getString("mobiles")+",15105314911",json.getString("orderNum"),json.getString("username"));
			Message mes = new Message();
			mes.setChannel(SendChannel.PUSH);
			mes.setType(MessageType.ORDER);
			mes.setSendTime(new Date());
			mes.setContent(msg);
			mes.setResult(str);
			mes.setReceiver("15105314911");
			mr.save(mes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(str.contains("发送成功")){
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @Description: 消息通知
	 * @param @param msg
	 * @param @param mobiles
	 * @param @return   
	 * @return boolean  
	 * @throws
	 * @author changjun
	 * @date 2015年11月5日
	 */
	@RequestMapping(value={ "/pushNews" },method = RequestMethod.POST)
	public boolean pushNews(String msg) {
		//msg={\"title\":\"天桥区扫街任务\",\"content\":\"天桥区可以开始扫啦啦啦啦!!!\",\"mobile\":\"18764157959\"}		
		 JSONObject json = new JSONObject(msg);
		 Message mes = new Message();
		 mes.setContent(msg);
		 mes.setSendTime(new Date());
		 mes.setReceiver(json.getString("mobile"));
		 mes.setChannel(SendChannel.PUSH);
		 mes.setType(MessageType.SYSTEM);
		 mr.save(mes);
		 String str = "";
		 try {
			 str = JpushClient.sendSimple("系统通知", json.getString("title"), json.getString("mobile")+",151",mes.getId(),"1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		 if(str.contains("发送失败")){
			 mr.updateMessageResult(str, mes.getId());
			 return false;
		 }
			 return true;
	}
	/**
	 * 
	 * @Description: 活动通知
	 * @param @param msg
	 * @param @return   
	 * @return boolean  
	 * @throws
	 * @author changjun
	 * @date 2015年11月5日
	 */
	@RequestMapping(value={"/pushActivi" },method = RequestMethod.POST)
	public boolean pushActivi(String msg) {
		 Message sm = new Message();
		 sm.setType(MessageType.ACTIVE);
		 sm.setChannel(SendChannel.PUSH);
		 sm.setContent(msg);
		 sm.setSendTime(new Date());
		 sm.setReceiver("all");
		 mr.save(sm);
		 String str = "";
		 try {
			  str = JpushClient.sendSimple("活动通知", msg, "all",sm.getId(),"2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		 if(str.contains("发送失败")){
			 mr.updateMessageResult(str, sm.getId());
			 return false;
		 }
			 return true;
	}
	/**
	 * 
	 * @Description: 拜访通知
	 * @param @param msg
	 * @param @param mobiles
	 * @param @return   
	 * @return boolean  
	 * @throws
	 * @author changjun
	 * @date 2015年11月5日
	 */
//	@RequestMapping(value={"/visitTask"},method = RequestMethod.POST)
//	public boolean visitTask(String msg,String mobile) {
//		String str = jpush.sendSimple("拜访通知", msg, mobile);
//		SimpleMessage sm = new SimpleMessage();
//		 sm.setChannel(MessageChannel.JIGUANGPUSH);
//		 sm.setContent(msg);
//		 sm.setCreateTime(new Date());
//		 sm.setReceiver(mobile);
//		 sm.setResult(str);
//		 smr.save(sm);
//		if(str.contains("发送成功")){
//			return true;
//		}
//		return false;
//	}
//	
//	/**
//	 * 
//	 * @Description: 收货款通知
//	 * @param @param msg
//	 * @param @param mobiles
//	 * @param @return   
//	 * @return boolean  
//	 * @throws
//	 * @author changjun
//	 * @date 2015年11月5日
//	 */
//	@RequestMapping(value={"/pushTakeMoney" },method = RequestMethod.POST)
//	public boolean pushTakeMoney(String msg,String mobile) {
//		String str = jpush.sendSimple("收货款通知", msg, mobile);
//		 SimpleMessage sm = new SimpleMessage();
//		 sm.setChannel(MessageChannel.JIGUANGPUSH);
//		 sm.setContent(msg);
//		 sm.setCreateTime(new Date());
//		 sm.setReceiver(mobile);
//		 sm.setResult(str);
//		 smr.save(sm);
//		if(str.contains("发送成功")){
//			return true;
//		}
//		return false;
//	}
}
