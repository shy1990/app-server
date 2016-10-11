package com.wangge.app.server.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.OrderSignfor;

public interface OrderSignforRepository extends JpaRepository<OrderSignfor, Long> {
  
  List<OrderSignfor> findByUserPhoneAndFastmailNo(String userPhone, String fastMailNo);
  
  OrderSignfor findByOrderNoAndUserPhone(String orderNo, String userPhone);
  
  OrderSignfor findByOrderNo(String orderno);
  @Query("select o from OrderSignfor o where o.userId=?1 and o.creatTime>=trunc(sysdate)- ?2 and o.creatTime<trunc(sysdate)-?3")
  Page<OrderSignfor> findByUserIdAndCreatTime(String userId,int startDate,int endDate, Pageable pageRequest);
 /* @Query("select sum(o.arrears) from OrderSignfor o where o.userId=?1 and o.creatTime>=trunc(sysdate) -?2 and o.creatTime<trunc(sysdate) -?3 ")
  Float findSumForArrears(String userId,long startDate,long endDate);*/
  @Query(value="select sum(o.arrears) from biz_order_signfor o where o.user_id=?1"+
" union"+
" select sum(o.arrears) from biz_order_signfor o where o.user_id= ?1 and o.creat_time>=trunc(sysdate) - 1 and o.creat_time<trunc(sysdate)"+
" union"+
" select sum(o.arrears) from biz_order_signfor o where o.user_id= ?1 and o.creat_time>=trunc(sysdate) - 2 and o.creat_time<trunc(sysdate)-1",nativeQuery=true)
  List<Float> findSumForArrears(String userId);
  
  /**
   * countByuserAndDayAndMonth:查询以业务员每天的订单统计和该月的订单统计. <br/>
   * 
   * @author yangqc
   * @param userId
   * @param day
   * @param month
   * @return
   * @since JDK 1.8
   */
  @Query(value = " select count(1), count(distinct s.shop_Name),\n"
      + "       sum(s.parts_Count), sum(s.phone_Count),  'dayCount'\n" + "  from biz_order_signfor s\n"
      + " where s.order_status not in (1,4) \n" + "   and s.user_id = ?1 \n" + "   and to_char(s.creat_time, 'yyyy-mm-dd') = ?2\n"
      + " union select count(1), count(distinct s.shop_Name),  sum(s.parts_Count),\n"
      + "       sum(s.phone_Count), 'cancleCount'\n" + "  from biz_order_signfor s\n" + " where s.order_status in (1,4) \n"
      + "   and s.user_id = ?1 \n" + "   and to_char(s.creat_time, 'yyyy-mm-dd') = ?2  union \n"
      + "select count(1),count(distinct s.shop_Name),\n" + "       sum(s.parts_Count),sum(s.phone_Count),'monthCount'\n"
      + "  from biz_order_signfor s\n" + " where s.order_status not in (1,4) "
          + "   and s.user_id = ?1 \n"
      + "   and to_char(s.creat_time, 'yyyy-mm') = ?3 ", nativeQuery = true)
  List<Object> countByuserAndDayAndMonth(String userId, String day, String month);
  
  /**
   * countByuserAndDay:查询一个业务员某天的历史订单量<br/>
   * 
   * @author yangqc
   * @param userId
   * @param month
   * @return
   * @since JDK 1.8
   */
  @Query(value = " select count(1), count(distinct s.shop_Name),\n"
      + "       sum(s.parts_Count), sum(s.phone_Count),  'dayCount'\n" + "  from biz_order_signfor s\n"
      + " where s.order_status not in (1,4) \n" + "   and s.user_id = ?1 \n" + "   and to_char(s.creat_time, 'yyyy-mm-dd') = ?2\n"
      + "union\n" + "select count(1), count(distinct s.shop_Name),  sum(s.parts_Count),\n"
      + "       sum(s.phone_Count), 'cancleCount'\n" + "  from biz_order_signfor s\n" + " where s.order_status in (1,4) \n"
      + "   and s.user_id = ?1 \n" + "   and to_char(s.creat_time, 'yyyy-mm-dd') = ?2 \n", nativeQuery = true)
  List<Object> countByuserAndDay(String userId, String day);

}
