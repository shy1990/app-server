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


  private List<OrderSignfor> findByFastmailNo(String fastMailNo) {
    return  osr.findByFastmailNo(fastMailNo);
  }

  public void updateOrderSignforList(String fastMailNo,String userPhone) {
    List<OrderSignfor> osList =  findByFastmailNo(fastMailNo);
            if(osList != null && !"".equals(userPhone)){
              for(OrderSignfor os : osList){
                os.setYewuSignforTime(new Date());
                os.setUserPhone(userPhone);
                os.setOrderStatus(2);
                osr.save(os);
              }    
            }
  }


}
