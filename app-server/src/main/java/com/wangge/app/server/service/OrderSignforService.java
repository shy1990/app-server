package com.wangge.app.server.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.event.afterSignforEvent;
import com.wangge.app.server.repository.OrderSignforRepository;
@Service
public class OrderSignforService {
  @Resource
  private OrderSignforRepository osr;
  
  @Resource
  private ApplicationContext ctx;

  public void saveOrderSignfor(OrderSignfor xlsOrder) {
    osr.save(xlsOrder);
    
  }



  public Date updateOrderSignforList(String fastMailNo,String userPhone,String signGeoPoint, int isPrimaryAccount,String userId,String childId) {
    Date date = new Date();
    List<OrderSignfor> osList =   osr.findByFastmailNo(fastMailNo);
            if(osList != null && osList.size() > 0){
                for(OrderSignfor os : osList){
                    os.setYewuSignforTime(date);
                    os.setYewuSignforGeopoint(signGeoPoint);
                    os.setUserPhone(userPhone);
                    os.setOrderStatus(2);
                    os.setIsPrimaryAccount(isPrimaryAccount);
                    osr.save(os);
                  //  ctx.publishEvent(new afterSignforEvent( userId, signGeoPoint,  isPrimaryAccount, childId,5));
                  }
                return date;
            }
            return null;
  }



  public void updateOrderSignfor(String orderNo, String userPhone,
      String signGeoPoint, int payType, String smsCode,int isPrimaryAccount,String userId,String childId,String  storePhone) {
      OrderSignfor orderSignFor =  findOrderSignFor(orderNo,userPhone);
      
         orderSignFor.setCustomSignforTime(new Date());
         orderSignFor.setCustomSignforGeopoint(signGeoPoint);
         orderSignFor.setOrderPayType(payType);
         orderSignFor.setOrderStatus(3);
         orderSignFor.setIsPrimaryAccount(isPrimaryAccount);
         if(smsCode != null && !"".equals(smsCode)){
           orderSignFor.setCustomSignforException(1);
         }else{
           orderSignFor.setCustomSignforException(0);
         }
         osr.save(orderSignFor);
         ctx.publishEvent(new afterSignforEvent( userId, signGeoPoint,  isPrimaryAccount, childId,6,storePhone));
     
  }



  public void updateOrderSignfor(String orderNo, String userPhone, String remark,String signGeoPoint,int isPrimaryAccount,String userId,String childId,String  storePhone) {
    OrderSignfor orderSignFor = findOrderSignFor(orderNo,userPhone);
    
      orderSignFor.setCustomUnSignRemark(remark);
      orderSignFor.setCustomSignforTime(new Date());
      orderSignFor.setCustomSignforGeopoint(signGeoPoint);
      orderSignFor.setOrderStatus(4);
      orderSignFor.setIsPrimaryAccount(isPrimaryAccount);
      osr.save(orderSignFor);
      ctx.publishEvent(new afterSignforEvent( userId, signGeoPoint,  isPrimaryAccount, childId,7,storePhone));
  }

  
  private OrderSignfor findOrderSignFor(String orderNo,String userPhone){
     return osr.findByOrderNoAndUserPhone(orderNo,userPhone);  
  }

}
