package com.wangge.app.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.Message;
import com.wangge.app.server.entity.OrderItem;
import com.wangge.app.server.entity.Message.MessageType;
import com.wangge.app.server.entity.Message.SendChannel;
import com.wangge.app.server.entity.Order;
import com.wangge.app.server.repository.MessageRepository;
import com.wangge.app.server.repository.OrderRepository;
import com.wangge.app.server.util.SortUtil;
import com.wangge.app.server.vo.OrderPub;

@RestController
@RequestMapping({"/v1/remind"})
public class RemindController {
	
//	private static final Logger logger = LoggerFactory.getLogger(RemindController.class);
	
	@Autowired
	private MessageRepository mr ;
	@Autowired
	private OrderRepository or;
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
	public ResponseEntity<Page<Message>> orderList(@RequestBody  JSONObject json){
		String receiver = json.getString("mobile");
		PageRequest pageRequest = SortUtil.buildPageRequest(json.getInteger("pageNumber"), json.getInteger("pageSize"), "push");
		Page<Message> list = mr.findByChannelAndTypeAndReceiverContaining(SendChannel.PUSH, MessageType.ORDER, receiver, pageRequest);
		return new ResponseEntity<Page<Message>>(list, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 根据订单号查看订单详情
	 * @param @param ordernum
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月29日
	 */
	@RequestMapping(value = "/selOrderDetail",method = RequestMethod.POST)
	public ResponseEntity<JSONObject> selOrderDetail(@RequestBody  JSONObject json){
		String orderNum = json.getString("orderNum");
		Order order = new Order();
		order = or.findOne(orderNum);
		JSONObject jo = new JSONObject();
		if(order.getId()!=null && !"".equals(order.getId())){
			StringBuffer sb = new StringBuffer();
			for (OrderItem item : order.getItems()) {
				sb.append(item.getName()+" ");
			}
			jo.put("username", order.getShopName());
			jo.put("amount", order.getAmount());
			jo.put("createTime", order.getCreateTime());
			jo.put("orderNum", order.getId());
			jo.put("shipStatus", order.getStatus());
			jo.put("goods", sb);
			jo.put("state", "正常订单");
			return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
		}
			jo.put("state", "未查询相关信息");
			return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
		
		
//		String username = json.getString("username");
//		OrderImpl opl = new OrderImpl();
//		List<OrderPub> order =opl.selOrderDetailByOrderNum(orderNum);
//		JSONObject jo = new JSONObject();
//		StringBuffer sb = new StringBuffer();
//		if(null != order && order.size()>0){
//			for(OrderPub o:order){
//				jo.put("username", username);
//				jo.put("createTime", o.getCreateTime());
//				jo.put("orderNum", o.getOrderNum());
//				jo.put("shipStatus", o.getShipStatus());
//				if(o.getName()!=null && !"null".equals(o.getName())){
//					sb.append(o.getName()+" ");
//				}
//				if(o.getStandard()!=null && !"null".equals(o.getStandard())){
//					sb.append("制式 ("+o.getStandard()+")");
//				}
//				if(o.getStorage()!=null && !"null".equals(o.getStorage())){
//					sb.append(" 内存 ( "+o.getStorage()+")");
//				}
//				if(o.getColorName()!=null && !"null".equals(o.getColorName())){
//					sb.append(" 颜色("+o.getColorName()+")");
//				}
//				if(o.getDealPrice()!=null && !"null".equals(o.getDealPrice())){
//					sb.append(" 单价 ("+o.getDealPrice()+"元)");
//				}
//				if(o.getNums()!=null && !"null".equals(o.getNums())){
//					sb.append(" 数量 ("+o.getNums()+"个) ;");
//				}
//				jo.put("amount", o.getAmount());
//			}
//			jo.put("goods", sb);
//			jo.put("state", "正常订单");
//			return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
//		}	
//			jo.put("state", "未查询相关信息");
//			return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
		
	}
	/**
	 * 
	 * @Description:消息通知列表
	 * @param @param username
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/newsRemindList",method = RequestMethod.POST)
	public ResponseEntity<Page<Message>> newsRemindList(@RequestBody  JSONObject json){
		String receiver = json.getString("mobile"); 
		PageRequest pageRequest = SortUtil.buildPageRequest(json.getInteger("pageNumber"), json.getInteger("pageSize"), "push");
		Page<Message> list = mr.findMessage(receiver,MessageType.SYSTEM, pageRequest);
		return new ResponseEntity<Page<Message>>(list, HttpStatus.OK);
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
//		String newsId  = json.getString("id");
		Message sm = mr.findOne(json.getLong("id"));
		return new ResponseEntity<Message>(sm, HttpStatus.OK);
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
//	@RequestMapping(value = "/activiRemind",method = RequestMethod.GET)
//	public ResponseEntity<Message> activiRemind(){
//		//查询正在进行中的活动  不区分已读未读 活动 type=2
//		Message msg = new Message();
////		msg.setContent("双11店庆");
////		msg.setCreateTime(new Date());
//		return new ResponseEntity<Message>(, HttpStatus.OK);
//	}
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
//	@RequestMapping(value = "/activiDetail",method = RequestMethod.POST)
//	public ResponseEntity<Message> activiDetail(@RequestBody  JSONObject json){
//		String id = json.getString("Id");
//		//根据id查询活动详情
//		Message msg = new Message();
//		msg.setContent("双11店庆");
//		msg.setCreateTime(new Date());
//		return new ResponseEntity<Message>(msg, HttpStatus.OK);
//	}
}
