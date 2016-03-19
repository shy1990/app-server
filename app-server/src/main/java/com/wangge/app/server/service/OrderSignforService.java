package com.wangge.app.server.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.repository.OrderSignforRepository;
@Service
public class OrderSignforService {
  @Resource
  private OrderSignforRepository osr;

  public void saveOrderSignfor(OrderSignfor xlsOrder) {
    osr.save(xlsOrder);
    
  }



  public Date updateOrderSignforList(String fastMailNo,String userPhone,String signGeoPoint) {
    Date date = new Date();
    List<OrderSignfor> osList =   osr.findByFastmailNo(fastMailNo);
            if(osList != null && osList.size() > 0){
                for(OrderSignfor os : osList){
                    os.setYewuSignforTime(date);
                    os.setYewuSignforGeopoint(signGeoPoint);
                    os.setUserPhone(userPhone);
                    os.setOrderStatus(2);
                    osr.save(os);
                  }
                return date;
            }
            return null;
  }



  public void updateOrderSignfor(String orderNo, String userPhone,
      String signGeoPoint, int payType, String smsCode) {
      OrderSignfor orderSignFor =  findOrderSignFor(orderNo,userPhone);
      
         orderSignFor.setCustomSignforTime(new Date());
         orderSignFor.setCustomSignforGeopoint(signGeoPoint);
         orderSignFor.setOrderPayType(payType);
         orderSignFor.setOrderStatus(3);
         if(smsCode != null && !"".equals(smsCode)){
           orderSignFor.setCustomSignforException(1);
         }else{
           orderSignFor.setCustomSignforException(0);
         }
         osr.save(orderSignFor);
     
  }



  public void updateOrderSignfor(String orderNo, String userPhone, String remark,String signGeoPoint) {
    OrderSignfor orderSignFor = findOrderSignFor(orderNo,userPhone);
    
      orderSignFor.setCustomUnSignRemark(remark);
      orderSignFor.setCustomSignforTime(new Date());
      orderSignFor.setCustomSignforGeopoint(signGeoPoint);
      orderSignFor.setOrderStatus(4);
      osr.save(orderSignFor);
  }

  
  private OrderSignfor findOrderSignFor(String orderNo,String userPhone){
     return osr.findByOrderNoAndUserPhone(orderNo,userPhone);  
  }

}
