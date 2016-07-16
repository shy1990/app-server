package com.wangge.app.server.count.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.app.server.repository.OrderSignforRepository;

/** 
  * ClassName: OrderCount <br/> 
  * Function: 本类为订单查询Domain类,目前提供功能:统计某个业务员在某天/某月的订单量
  *  并将这些数据封装为OrderCount实例;
  * Reason: TODO ADD REASON(可选). <br/> 
  * date: 2016年7月15日 下午2:48:11 <br/> 
  * 
  * @author yangqc 
  * @version  
  * @since JDK 1.8 
  */  
@Service
public class OrderCount  {
  @Autowired
  OrderSignforRepository orderRep;
  //订单数量
  private Long orderCount;
  //客户数量(本系统客户为商铺)
  private Long shopCount;
  //手机数量
  private Long phoneCount;
  //其他产品数量
  private Long partCount;
  
  public Long getOrderCount() {
    return orderCount;
  }
  
  public void setOrderCount(Long orderCount) {
    this.orderCount = orderCount;
  }
  
  public Long getShopCount() {
    return shopCount;
  }
  
  public void setShopCount(Long shopCount) {
    this.shopCount = shopCount;
  }
  
  public Long getPhoneCount() {
    return phoneCount;
  }
  
  public void setPhoneCount(Long phoneCount) {
    this.phoneCount = phoneCount;
  }
  
  public Long getPartCount() {
    return partCount;
  }
  
  public void setPartCount(Long partCount) {
    this.partCount = partCount;
  }
  
  public OrderCount() {
    super();
  }
  
  public OrderCount(Long orderCount, Long shopCount, Long phoneCount, Long partCount) {
    super();
    this.orderCount = orderCount;
    this.shopCount = shopCount;
    this.phoneCount = phoneCount;
    this.partCount = partCount;
  }
  
  private OrderCount getOrderCount(Object[] count) {
    Long sum0 = count[0] == null ? 0 : Long.valueOf(count[0].toString());
    Long sum1 = count[1] == null ? 0 : Long.valueOf(count[1].toString());
    Long sum2 = count[2] == null ? 0 : Long.valueOf(count[3].toString());
    Long sum3 = count[3] == null ? 0 : Long.valueOf(count[2].toString());
    return new OrderCount(sum0, sum1, sum2, sum3);
  }
  
  public Map<String, Object> countByDayAndMonth(String userId, String day,String month) {
    Map<String, Object> dayMap = new HashMap<String, Object>();
    
    List<Object> countList = orderRep.countByuserAndDayAndMonth(userId, day, month);
    countList.stream().forEach((o->{
      Object[] orderCount=(Object[])o;
      dayMap.put(orderCount[4].toString(),  getOrderCount(orderCount));
    }));
    return dayMap;
  }
  
  public Map<String, Object> countByDay(String userId, String day) {
  Map<String, Object> dayMap = new HashMap<String, Object>();
    
    List<Object> countList = orderRep.countByuserAndDay(userId, day);
    countList.stream().forEach((o->{
      Object[] orderCount=(Object[])o;
      dayMap.put(orderCount[4].toString(),  getOrderCount(orderCount));
    }));
    return dayMap;
  }
}
