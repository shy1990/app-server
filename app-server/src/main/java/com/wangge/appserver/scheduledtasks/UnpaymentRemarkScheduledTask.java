package com.wangge.appserver.scheduledtasks;



import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wangge.app.server.entity.UnpaymentRemark;
import com.wangge.app.server.repositoryimpl.OrderImpl;
import com.wangge.app.server.service.UnpaymentRemarkService;

@Component
@EnableScheduling
public class UnpaymentRemarkScheduledTask {
  @Resource
  private OrderImpl orderImpl;
  @Resource
  private UnpaymentRemarkService urs;
  
  
  @Scheduled(cron = "0 0 15 ? * *")
  public void exsitUnpaymentRemarkStatus(){
     List<UnpaymentRemark> remarkList = urs.findByCreateTime(new Date());
     if(remarkList != null && remarkList.size() > 0){
       for(UnpaymentRemark u : remarkList){
         Map<String,String> map = orderImpl.findOrderPayStatusByOrderNum(u.getOrderno());
         if(map.get("payStatus") != null){
        	 if("1".equals(map.get("payStatus"))){
                 u.setStatus(1);
                
               }else{
                 u.setStatus(2);
               } 
         }
         
         urs.saveUnpaymentRemark(u);
       }
     
     }
     
  }

}
