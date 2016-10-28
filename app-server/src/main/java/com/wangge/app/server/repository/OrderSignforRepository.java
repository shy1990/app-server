package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.OrderSignfor;

public interface OrderSignforRepository extends JpaRepository<OrderSignfor, Long> {
  
  List<OrderSignfor> findByUserPhoneAndFastmailNo(String userPhone, String fastMailNo);
  
  OrderSignfor findByOrderNoAndUserPhone(String orderNo, String userPhone);
  
  OrderSignfor findByOrderNo(String orderno);
  
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

  /**
   * findByMemberPhoneAndCreatTime:查询某个店铺前天晚上8点到昨天晚上9点的订单<br/>
   *
   * @param memberPhone
   * @return List<OrderSignfor>
   * @since JDK 1.8
   */
  @Query(value = "select *\n" +
                  "  from BIZ_ORDER_SIGNFOR bos\n" +
                  " where bos.creat_time between\n" +
                  "       (select trunc(sysdate - 2) + 20 / 24 from dual) and\n" +
                  "       (select trunc(sysdate - 1) + 21 / 24 from dual)\n" +
                  "   and bos.member_phone = ?1", nativeQuery = true)
  List<OrderSignfor> findByMemberPhoneAndCreatTime(String memberPhone);
}
