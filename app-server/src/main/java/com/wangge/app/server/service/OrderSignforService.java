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
                if((!"".equals(signGeoPoint) && signGeoPoint != null)&& (!"".equals(userPhone) && userPhone != null)){
                  os.setYewuSignforTime(date);
                  os.setYewuSignforGeopoint(signGeoPoint);
                  os.setUserPhone(userPhone);
                  os.setOrderStatus(2);
                  osr.save(os);
                  return date;
                }
              }    
            }
            return null;
  }


}
