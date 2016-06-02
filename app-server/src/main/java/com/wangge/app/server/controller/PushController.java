package com.wangge.app.server.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Message;
import com.wangge.app.server.entity.Message.MessageType;
import com.wangge.app.server.entity.Message.SendChannel;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.jpush.client.JpushClient;
import com.wangge.app.server.repository.RegistDataRepository;
import com.wangge.app.server.repositoryimpl.OrderImpl;
import com.wangge.app.server.service.MessageService;
import com.wangge.app.server.service.OrderSignforService;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.app.server.service.RegistDataService;

@RestController
@RequestMapping({ "/v1/push"})
public class PushController {
  
//  private static final Logger logger = LoggerFactory.getLogger(PushController.class);
  
  @Resource
  private MessageService mr;
  @Autowired
  private OrderImpl op;
  @Resource
  private OrderSignforService orderSignforService;
  @Resource 
  private SalesmanService salesmanService;
  
  @Resource
  private RegistDataService registDataService;
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
//    StringBuffer mobiles  = new StringBuffer();
    
    
    
    /**
     * 【20151029164718792】山东省潍坊市青州市潍坊市青州市|弥河镇慧霞手机店下单成功，
     * 订单商品:标准版Samsung/三星 三星SM-G5308W*1台,标准版Xiaomi/小米 红米 红米note2*2台
     *{"orderNum":"222222222222222","mobiles":"1561069 62989","amount":"10.0","username":"天桥魅族店"}
     */
    
    JSONObject json = new JSONObject(msg);
    String mobile = json.getString("mobiles");
    String accCount = json.getString("accNum");
    String ss = json.getString("username");
    int skuNum = Integer.parseInt(json.getString("skuNum"));
    Float amount = Float.parseFloat(json.getString("amount"));
    String orderno = json.getString("orderNum");
    if(ss.contains("市")){
      ss = ss.substring(ss.indexOf("市")+1,ss.length());
    }
    String send = ss+",数量:"+skuNum+",金额:"+amount+",订单号:"+orderno;
  
   
    String str = "";
    try {
      
      Message mes = new Message();
      mes.setChannel(SendChannel.PUSH);
      mes.setType(MessageType.ORDER);
      mes.setSendTime(new Date());
      mes.setContent(msg);
      mes.setReceiver(mobile);
      mr.save(mes);
      OrderSignfor o = new OrderSignfor();
      o.setOrderNo(orderno);
      o.setCreatTime(new Date());
      o.setOrderPrice(amount);
      o.setPhoneCount(skuNum);
      o.setOrderStatus(0);
      o.setShopName(ss);
      o.setUserId(salesmanService.findByMobile(mobile).getId());
      o.setUserPhone(mobile);
      o.setPartsCount(Integer.parseInt(accCount));
      
      orderSignforService.saveOrderSignfor(o);
      
      if(null!=salesmanService.findByMobile(mobile)){
        str = JpushClient.sendOrder("下单通知", send,mobile,json.getString("orderNum"),json.getString("skuNum"),json.getString("accNum"),"0");
      }
      
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
     if(str.contains("发送失败")){
     //  mr.updateMessageResult(str, mes.getId());
       return false;
     }
       return true;
  }
  /**
   * 
   * @Description: 取消订单推送
   * @param @param msg
   * @param @return   
   * @return boolean  
   * @throws
   * @author changjun
   * @date 2015年11月5日
   */
  @RequestMapping(value = { "/cancelOrder"},method = RequestMethod.POST)
  public boolean  cancelOrder(String msg){
    /**
     * 【20151029164718792】山东省潍坊市青州市潍坊市青州市|弥河镇慧霞手机店下单成功，
     * 订单商品:标准版Samsung/三星 三星SM-G5308W*1台,标准版Xiaomi/小米 红米 红米note2*2台
     *{"orderNum":"222222222222222","mobiles":"1561069 62989","amount":"10.0","username":"天桥魅族店"}
     */
    
    JSONObject json = new JSONObject(msg);
    String send = json.getString("username")+",订单号:"+json.getString("orderNum");
    orderSignforService.updateMessageType(1, json.getString("orderNum"));
    String str = "";
    System.out.println(json.getString("mobiles"));
    try {
      str = JpushClient.sendOrder("取消订单通知", send,json.getString("mobiles"),json.getString("orderNum"),json.getString("skuNum"),json.getString("accNum"),"0");
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
       str = JpushClient.sendSimple("系统通知", json.getString("title"), json.getString("mobile"),mes.getId(),"1");
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
  public boolean pushActivi(String msg,String mobiles) {
     JSONObject json = new JSONObject(msg);
     List<Message> list = new ArrayList<Message>(); 
     if(mobiles!=null && mobiles.contains(",")){
       String[] ss = mobiles.split(",");
       for(String s:ss){
         Message mg = new Message();
         mg.setType(MessageType.ACTIVE);
         mg.setChannel(SendChannel.PUSH);
         mg.setSendTime(new Date());
         mg.setContent(json.getString("content"));
         mg.setReceiver(s);   
         list.add(mg);
       }
       }else{
         Message mg = new Message();
         mg.setType(MessageType.ACTIVE);
         mg.setChannel(SendChannel.PUSH);
         mg.setSendTime(new Date());
         mg.setContent(msg);
         mg.setReceiver(mobiles);
         list.add(mg);
       }
     Long id = null;
     if(list!=null && list.size()>0){
      id =  op.addActivityMsg(list);
     }
     if(id!=0){
       try {
          JpushClient.sendSimple("活动通知", json.getString("title"), mobiles,id,"2");
      } catch (Exception e) {
        e.printStackTrace();
      }
       
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
//  @RequestMapping(value={"/visitTask"},method = RequestMethod.POST)
//  public boolean visitTask(String msg,String mobile) {
//    String str = jpush.sendSimple("拜访通知", msg, mobile);
//    SimpleMessage sm = new SimpleMessage();
//     sm.setChannel(MessageChannel.JIGUANGPUSH);
//     sm.setContent(msg);
//     sm.setCreateTime(new Date());
//     sm.setReceiver(mobile);
//     sm.setResult(str);
//     smr.save(sm);
//    if(str.contains("发送成功")){
//      return true;
//    }
//    return false;
//  }
//  
//  /**
//   * 
//   * @Description: 收货款通知
//   * @param @param msg
//   * @param @param mobiles
//   * @param @return   
//   * @return boolean  
//   * @throws
//   * @author changjun
//   * @date 2015年11月5日
//   */
//  @RequestMapping(value={"/pushTakeMoney" },method = RequestMethod.POST)
//  public boolean pushTakeMoney(String msg,String mobile) {
//    String str = jpush.sendSimple("收货款通知", msg, mobile);
//     SimpleMessage sm = new SimpleMessage();
//     sm.setChannel(MessageChannel.JIGUANGPUSH);
//     sm.setContent(msg);
//     sm.setCreateTime(new Date());
//     sm.setReceiver(mobile);
//     sm.setResult(str);
//     smr.save(sm);
//    if(str.contains("发送成功")){
//      return true;
//    }
//    return false;
//  }
  
  
  @RequestMapping(value = { "/pushNewAfterSales"},method = RequestMethod.POST)
  public boolean pushNewAfterSales(String msg){
    JSONObject json = new JSONObject(msg);
    String mobile = json.getString("mobile");
    String content = json.getString("content");
    String str = "";
    try {
      
      Message mes = new Message();
      mes.setChannel(SendChannel.PUSH);
      mes.setType(MessageType.SHOUHOU);
      mes.setSendTime(new Date());
      mes.setContent(content);
      mes.setReceiver(mobile);
      mr.save(mes);
      
      str = JpushClient.sendSimple("售后通知", content, mobile,mes.getId(),"3");//3代表售后通知
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
     if(str.contains("发送失败")){
     //  mr.updateMessageResult(str, mes.getId());
       return false;
     }
       return true;
  }
}
