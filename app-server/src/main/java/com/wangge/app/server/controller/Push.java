package com.wangge.app.server.controller;



import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Message;
import com.wangge.app.server.repository.MessageRepository;
import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.app.server.util.JPush;
import com.wangge.app.server.util.JpushClient;

@RestController
@RequestMapping({ "/push" })
public class Push{
	
	private static final Logger logger = LoggerFactory.getLogger(Push.class);
	
	@Autowired
	private static JpushClient jpush;
	@Autowired
	private SalesmanRepository sale;
	@PersistenceContext
	private EntityManager em;
//	@Autowired
//	private UtilImpl ui;
//	@Autowired
//	private OrderRepository order;
	
//	@Autowired
//	private TaskRepository task;
	
	@Autowired
	private MessageRepository mes;
	
	/**
	 * 
	 * @Description: 新订单提醒
	 * @param @param state
	 * @param @param info
	 * @param @return
	 * @return ResponseEntity<JSONObject>
	 * @throws ParseException 
	 * @throws
	 * @author changjun
	 * @date 2015年10月23日
	 */
	@RequestMapping(value = { "/pushNewOrder" },method = RequestMethod.POST)
	public boolean pushNewOrder(String msg,String mobiles){
		/**
		 * 会员下单通知:【222222222222222】山东省济南市历下区天桥店下单成功，订单商品
		 */
//		put("info", "logan");
////	JSONObject jsonStr = JSONObject.fromObject(map);
//		 JSONArray array = new JSONArray();
//		  array.put(map);
//		Order or = null;
//		Task ta = null;
//		if(mobiles!=null && !"".equals(mobiles)){
////			sa = sale.findPidByPhone(mobiles);
////			or = order.findByUsername(mobiles);
//			ta = task.findOneByName(mobiles);
//		}
		//String title,String msg,String alias,String type
		String str = jpush.send("下单通知", msg, mobiles);
		Message message = new Message();
		message.setCreateTime(new Date());
		message.setContent(msg);
		message.setReceiver(mobiles);
		message.setResult(str);
		message.setType("0");
		mes.save(message);
		if(str.contains("发送成功")){
			return true;
		}
		return false;
		
	}

	/**
	 * 
	 * @Description: 系统通知
	 * @param @param title
	 * @param @param msg
	 * @param @param mobiles
	 * @param @return   
	 * @return boolean  
	 * @throws
	 * @author changjun
	 * @date 2015年10月30日
	 */
	@RequestMapping(value={ "/pushNews" },method = RequestMethod.POST)
	public boolean pushNews(String title,String msg,String mobiles) {
		if(mobiles!=null && !"".equals(mobiles)){
			String str = jpush.send(title, msg, mobiles);
			Message message = new Message();
			message.setCreateTime(new Date());
			message.setContent(msg);
			message.setReceiver(mobiles);
			message.setResult(str);
			message.setType("0");
			mes.save(message);
			if(str.contains("发送成功")){
				return true;
			}
			return false;
			
		}
		return false;
	}

	/**
	 * 
	 * @Description: 活动通知
	 * @param @return
	 * @return ResponseEntity<JSONObject>
	 * @throws
	 * @author changjun
	 * @date 2015年10月23日
	 */
	@RequestMapping(value={"/pushActivi" },method = RequestMethod.POST)
	public boolean pushActivi(String msg) {
		String str = jpush.send("活动通知", msg, "all");
		Message message = new Message();
		message.setCreateTime(new Date());
		message.setContent(msg);
		message.setReceiver("all");
		message.setResult(str);
		message.setType("0");
		mes.save(message);
		if(str.contains("发送成功")){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Description: 拜访任务提醒
	 * @param @return
	 * @return ResponseEntity<JSONObject>
	 * @throws
	 * @author changjun
	 * @date 2015年10月23日
	 */
	@RequestMapping(value={"/visitTask"},method = RequestMethod.POST)
	public boolean visitTask(String msg,String mobiles) {
		try {
			System.out.println(mobiles+"***"+msg);
			JPush.sendMessageV3("all", mobiles, msg, "拜访通知", null);
		} catch (org.apache.http.ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
//		String str = jpush.send("拜访通知", msg, mobiles);
//		Message message = new Message();
//		message.setCreateTime(new Date());
//		message.setContent(msg);
//		message.setReceiver(mobiles);
//		message.setResult(str);
//		message.setType("0");
//		mes.save(message);
//		if(str.contains("发送成功")){
//			return true;
//		}
		return false;
	}

	/**
	 * 
	 * @Description: 任务-收货款
	 * @param @return
	 * @return ResponseEntity<JSONObject>
	 * @throws
	 * @author changjun
	 * @date 2015年10月23日
	 */
	@RequestMapping(value={"/pushTakeMoney" },method = RequestMethod.POST)
	public boolean pushTakeMoney(String msg,String mobiles) {
		String str = jpush.send("收货款通知", msg, mobiles);
		Message message = new Message();
		message.setCreateTime(new Date());
		message.setContent(msg);
		message.setReceiver(mobiles);
		message.setResult(str);
		message.setType("0");
		mes.save(message);
		if(str.contains("发送成功")){
			return true;
		}
		return false;
	}
}