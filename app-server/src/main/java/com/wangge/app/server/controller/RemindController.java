package com.wangge.app.server.controller;



import javax.annotation.Resource;


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
import com.wangge.app.server.repository.OrderRepository;
import com.wangge.app.server.service.MessageService;
import com.wangge.app.server.util.SortUtil;

@RestController
@RequestMapping({"/v1/remind"})
public class RemindController {
  
//  private static final Logger logger = LoggerFactory.getLogger(RemindController.class);
  
  @Resource
  private MessageService mr ;
  @Resource
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
    Page<Message> list = mr.findByChannelAndTypeAndReceiverContaining(SendChannel.PUSH,receiver,"orderNum", pageRequest);
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
    int skuNum = Integer.parseInt(json.getString("skuNum"));
    int accNum = Integer.parseInt(json.getString("accNum"));
    Order order = or.findOne(orderNum);
    JSONObject jo = new JSONObject();
    if(order!=null && !"".equals(order.getId())){
      StringBuffer sb = new StringBuffer();
      for (OrderItem item : order.getItems()) {
        sb.append(item.getName()+" ");
      }
      jo.put("username", order.getShopName());
      jo.put("amount", order.getAmount());
      jo.put("createTime", order.getCreateTime());
      jo.put("orderNum", order.getId());
      jo.put("shipStatus", order.getStatus().ordinal());
      jo.put("goods", sb);
      jo.put("skuNum", skuNum);
      jo.put("itemOtherNum", accNum);
      jo.put("customMobile", order.getMobile());
      jo.put("state", "正常订单");
      return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
    }
      jo.put("state", "未查询相关信息");
      return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
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
    Page<Message> list = mr.findMessageByReceiver(receiver, pageRequest);
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
//    String newsId  = json.getString("id");
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
  @RequestMapping(value = "/activiList",method = RequestMethod.POST)
  public ResponseEntity<Page<Message>> activiRemind(@RequestBody  JSONObject json){
    //查询正在进行中的活动  不区分已读未读 活动 type=2
    PageRequest pageRequest = SortUtil.buildPageRequest(json.getInteger("pageNumber"), json.getInteger("pageSize"), "push");
    Page<Message> list = mr.findMessageByType(MessageType.ACTIVE, pageRequest);
    return new ResponseEntity<Page<Message>>(list, HttpStatus.OK);
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
//  @RequestMapping(value = "/activiDetail",method = RequestMethod.POST)
//  public ResponseEntity<Message> activiDetail(@RequestBody  JSONObject json){
//    String id = json.getString("Id");
//    //根据id查询活动详情
//    Message msg = new Message();
//    msg.setContent("双11店庆");
//    msg.setCreateTime(new Date());
//    return new ResponseEntity<Message>(msg, HttpStatus.OK);
//  }
}
