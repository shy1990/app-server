package com.wangge.app.server.controller;



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
	 * @throws
	 * @author changjun
	 * @date 2015年10月23日
	 */
	@RequestMapping(value = { "/pushNewOrder" },method = RequestMethod.POST)
	public boolean pushNewOrder(String msg,String mobiles) {
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
		message.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
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
	 * @Description: 消息通知
	 * @param @return
	 * @return ResponseEntity<JSONObject>
	 * @throws
	 * @author changjun
	 * @date 2015年10月23日
	 */
	@RequestMapping(value={ "/pushNews" },method = RequestMethod.POST)
	public boolean pushNews(String msg,String mobiles) {
		if(mobiles!=null && !"".equals(mobiles)){
//			return jpush.send("系统通知", msg, mobiles);
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
	@RequestMapping({ "/pushActivi" })
	public boolean pushActivi(String msg) {
//		return jpush.send("系统通知", msg, "all");
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
	@RequestMapping({ "/visitTask" })
	public boolean visitTask(String msg,String mobiles) {
//		return jpush.send("系统通知", msg, mobiles);
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
	@RequestMapping({ "/pushTakeMoney" })
	public boolean pushTakeMoney(String msg,String mobiles) {
//		return jpush.send("系统通知", msg, mobiles);
		return false;
	}
}