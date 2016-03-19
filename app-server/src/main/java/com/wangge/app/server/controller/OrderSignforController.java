package com.wangge.app.server.controller;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.pojo.MessageCustom;
import com.wangge.app.server.pojo.QueryResult;
import com.wangge.app.server.repositoryimpl.OrderSignforImpl;
import com.wangge.app.server.service.OrderSignforService;

@RestController
@RequestMapping("/v1/remind")
public class OrderSignforController {
  
  private static final Logger logger = LoggerFactory.getLogger(OrderSignforController.class);
  @Resource
  private OrderSignforService orderSignforService;
  
  @Resource
  private OrderSignforImpl osi;
  /**
   * @throws ParseException 
   * @throws NumberFormatException 
   * @throws ParseException 
   * @throws NumberFormatException 
   * 
  * @Title: getOrderSignforList 
  * @Description: TODO(获取物流单号列表) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<Page<OrderSignfor>>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/getBussOrderList" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<QueryResult<OrderSignfor>> getOrderSignforList(@RequestBody JSONObject jsons){
	  int pageNo = jsons.getIntValue("pageNumber");
	  int pageSize = jsons.getIntValue("pageSize");
    String userPhone = jsons.getString("userPhone") ;
    QueryResult<OrderSignfor> qr = osi.getOrderSignforList(userPhone, pageNo > 0 ? pageNo-1 : 0,pageSize > 0 ? pageSize : 10);   
    return new ResponseEntity<QueryResult<OrderSignfor>>(qr,HttpStatus.OK);
  }
  /**
   * 
   * 
  * @Title: bussOrderSignFor 
  * @Description: TODO(业务签收) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<message>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/bussOrderSignFor", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<MessageCustom> bussOrderSignFor(@RequestBody JSONObject jsons){
      String fastMailNo = jsons.getString("fastmailNo");
      String userPhone = jsons.getString("userPhone");
      String signGeoPoint = jsons.getString("signGeoPoint");
      MessageCustom m = new MessageCustom();
      try {
        
        Date signTime = orderSignforService.updateOrderSignforList(fastMailNo,userPhone,signGeoPoint);
        if(signTime != null){
          m.setMsg("success");
          m.setCode("0");
          m.setSignTime(signTime);
        }else{
          m.setMsg("false");
          m.setCode("1");
        }
        
      } catch (Exception e) {
        e.printStackTrace();
        m.setMsg("false");
        m.setCode("1");
      }
      return new ResponseEntity<MessageCustom>(m,HttpStatus.OK);
  }
  /**
   * @throws ParseException 
   * @throws NumberFormatException 
   * 
  * @Title: getOrderList 
  * @Description: TODO(这里用一句话描述这个方法的作用) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<List<OrderSignfor>>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<QueryResult<OrderSignfor>> getOrderList(@RequestBody JSONObject jsons){
                String userPhone = jsons.getString("userPhone");
                String type = jsons.getString("type");
                int pageNo = jsons.getIntValue("pageNumber");
                int pageSize = jsons.getIntValue("pageSize");
               QueryResult<OrderSignfor> qr = osi.getOrderList(userPhone, type, pageNo > 0 ? pageNo-1 : 0,pageSize > 0 ? pageSize : 10);
         //       QueryResult<OrderSignfor> qr = osi.getOrderList(userPhone, type, pageNo-1,pageSize != 0 ? pageSize: 10);
    return new ResponseEntity<QueryResult<OrderSignfor>>(qr,HttpStatus.OK);
  }
  /**
   * 
  * @Title: customOrderSign 
  * @Description: TODO(客户签收) 
  * @param @param jsons
  * @param @param orderSignfor
  * @param @return    设定文件 
  * @return ResponseEntity<MessageCustom>    返回类型 
  * @throws
   */
  @RequestMapping(value ="/customOrderSign", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<MessageCustom> customOrderSign(@RequestBody JSONObject jsons){
    String userPhone = jsons.getString("userPhone");
    String orderNo = jsons.getString("orderNo");
    String smsCode = jsons.getString("smsCode");
    int payType =  jsons.getIntValue("payType");
    String signGeoPoint = jsons.getString("signGeoPoint");
    MessageCustom m = new MessageCustom();
    try {
      if((signGeoPoint != null && !"".equals(signGeoPoint))&& payType >= 0){
        orderSignforService.updateOrderSignfor(orderNo, userPhone, signGeoPoint,payType,smsCode);
        m.setMsg("success");
        m.setCode("0");
      }else{
        m.setMsg("false");
        m.setCode("1");
      }
      
    } catch (Exception e) {
      m.setMsg("false");
      m.setCode("1");
     /* logger.error("OrderSignforController updateOrderSignfor error :"+e);
      logger.debug("OrderSignforController updateOrderSignfor error :"+e);
      logger.info("OrderSignforController updateOrderSignfor error :"+e);*/
    }
    
   return  new ResponseEntity<MessageCustom>(m, HttpStatus.OK);
  }
  /**
   * 
  * @Title: customOrderUnSign 
  * @Description: TODO(客户拒签) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<MessageCustom>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/customOrderUnSign", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<MessageCustom> customOrderUnSign(@RequestBody JSONObject jsons){
    String userPhone = jsons.getString("userPhone");
    String orderNo = jsons.getString("orderNo");
    String remark = jsons.getString("remark");
    MessageCustom m = new MessageCustom();
    try {
      if(remark!= null && !"".equals(remark)){
        orderSignforService.updateOrderSignfor(orderNo, userPhone, remark);
        m.setMsg("success");
        m.setCode("0");
      }else{
        m.setMsg("备注不能为空");
        m.setCode("1");
      }
     
    } catch (Exception e) {
      m.setMsg("false");
      m.setCode("1");
      logger.error("OrderSignforController customOrderUnSign() error :"+e);
    }
    return new ResponseEntity<MessageCustom>(m,HttpStatus.OK);
    
  }
  
  
  

}
