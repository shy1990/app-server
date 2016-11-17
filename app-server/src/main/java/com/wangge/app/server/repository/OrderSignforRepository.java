package com.wangge.app.server.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.entity.dto.OrderDto;
import com.wangge.app.server.vo.OrderVo;
import org.springframework.transaction.annotation.Transactional;

public interface OrderSignforRepository extends JpaRepository<OrderSignfor, Long> {
  
  List<OrderSignfor> findByUserPhoneAndFastmailNo(String userPhone, String fastMailNo);
  
  OrderSignfor findByOrderNoAndUserPhone(String orderNo, String userPhone);
  
  OrderSignfor findByOrderNo(String orderno);
  /***************************对账单***********************************/
  @Query("select o.shopName,o.orderNo,o.orderPayType,o.orderPrice,o.creatTime,o.arrears,o.billStatus,o.isPrimaryAccount,o.orderStatus,o.actualPayNum from OrderSignfor o  where o.userId= ?1 and o.fastmailTime = trunc(sysdate -?2) and ( o.orderStatus = '0' or  o.orderStatus = '2' or o.orderStatus = '3')")
  Page<Object> findByUserIdAndCreatTime(String userId,int startDate, Pageable pageRequest);
 /* @Query("select sum(o.arrears) from OrderSignfor o where o.userId=?1 and o.creatTime>=trunc(sysdate) -?2 and o.creatTime<trunc(sysdate) -?3 ")
  Float findSumForArrears(String userId,long startDate,long endDate);*/
	@Query(value = "  select (select sum(o.arrears) "
               +  "   from biz_order_signfor o "
               +  "  where o.user_id = ?1"
                  +   " and o.fastmail_time = trunc(sysdate -1)"
                  
                   + " and o.order_status = '3') as todayArrears,"
                 +" (select sum(o.arrears) "
                 +   " from biz_order_signfor o"
                 +  " where o.user_id = ?1"
                 +   " and o.fastmail_time = trunc(sysdate - 2) "
                   +  " and o.order_status = '3') as yesterdayArrears,"
                + " (select sum(o.arrears)"
                +   " from biz_order_signfor o "
                +  " where o.user_id = ?1"
                +    " and o.fastmail_time < trunc(sysdate) -1"
                +     " and o.order_status = '3') as historyArrears"
           + " from dual", nativeQuery = true)
	List<Object> findSumForArrears(String userId);
	@Query(value = "  select   ( select sum(o.actual_pay_num) "
                +"    from biz_order_signfor o "
                +"  where o.user_id = ?1 "
                +"    and o.fastmail_time = trunc(sysdate - 1) "
                     +" and (o.order_status = '0' or o.order_status = '2') ) as todayArrears , "
                    +" (select sum(o.actual_pay_num)  "
                      +"      from biz_order_signfor o "
                     +"      where o.user_id = ?1 "
                        +"     and o.fastmail_time = trunc(sysdate - 2) "
                         +"    and (o.order_status = '0' or o.order_status = '2')) as "
                +"   yesterdayArrears, "

                     +"    (select sum(o.actual_pay_num) "
                    +"        from biz_order_signfor o "
                     +"      where o.user_id = ?1 "
                      +"       and o.fastmail_time < trunc(sysdate  -1) "
                       +"      and (o.order_status = '0' or o.order_status = '2')) as historyArrears "

                  +"  from dual", nativeQuery = true)
	List<Object> findSumForArrearsUign(String userId);

	/*@Query( " select count(*) as shopCount ,o.orderCount,o.shopArrears,o.createTime from ( select count(os.id) as orderCount,sum(os.arrears) as shopArrears, os.shopName,trunc(os.creatTime) as createTime from  OrderSignfor os where os.arrears > 0 and os.userId = ?1 group by os.shopName,trunc(os.creatTime)) o group by o.shopArrears,o.orderCount,o.createTime")
  Page<Object> findByUserId(String userId, Pageable pageRequest);*/

	@Query(value = " select * from (select * from ( "
			+ " select ROWNUM AS RN,t.* from (  "
			+ " select o.orderCount,o.dayArrears,o.daytime,os.shopCount from "
			+ " (select sum(t.orderCount) as orderCount,"
			+ " sum(t.dayArrears) as dayArrears,"
			+ " trunc(t.daytime)  as daytime  from ("
			+ " select count(osf.signid) as orderCount,"
			+ " sum(osf.arrears) as dayArrears,"
			+ " trunc(osf.fastmail_time) + 1  as daytime"
			+ " from biz_order_signfor osf"

			+ " where osf.order_status = '3' " + " and osf.user_id = ?1 "

            + " and osf.fastmail_time < trunc(sysdate - 1)  "
			+ " group by trunc(osf.fastmail_time)"

			+ " union "

			+ " select count(osf.signid) as orderCount,"
			+ " sum(osf.actual_pay_num) as dayArrears,"
			+ " trunc(osf.fastmail_time) + 1  as daytime "
			+ " from biz_order_signfor osf"

			+ " where (osf.order_status = '0' or  osf.order_status = '2')"
			+ " and osf.user_id = ?1"
			+ " and osf.fastmail_time < trunc(sysdate - 1)  "

			+ " group by trunc(osf.fastmail_time)"

			+ " ) t group by t.daytime order by t.daytime desc"



					+" ) o left join "


					+ " (select count(*) as shopCount, t.fastmail_time"
					+ " from (select trunc(osr.fastmail_time) + 1 as fastmail_time,"
					+ " osr.shop_name "
					+ " from biz_order_signfor osr "
					+ " where osr.order_status = '3'  "
					+ " and osr.fastmail_time < trunc(sysdate - 1)  "
					+ " and osr.user_id = ?1 "

					+ " group by trunc(osr.fastmail_time), osr.shop_name "

					+ " union "

					+ " select trunc(osr.fastmail_time) + 1 as fastmail_time,"
					+ " osr.shop_name "
					+ " from biz_order_signfor osr "
					+ " where (osr.order_status = '0' or  osr.order_status = '2')"
					+ " and osr.fastmail_time < trunc(sysdate - 1)  "
					+ " and osr.user_id = ?1 "

					+ " group by trunc(osr.fastmail_time), osr.shop_name) t"
					+ " group by t.fastmail_time "
					+ " ) os on os.fastmail_time = o.daytime order by o.daytime desc"

					+ "	)t	"
					+ " )  where RN <= ?2*?3) where  RN>(?2-1)*?3", nativeQuery = true)
  List<Object> findBillHistoryConfluenceList(String userId,int pageNo,int pageSize);

	@Query(value = "select count(*) "
			+ " from (select sum(t.orderCount) as orderCount, "
			+ " sum(t.dayArrears) as dayArrears, "
			+ " trunc(t.daytime)+1 as daytime "
			+ " from (select count(osf.signid) as orderCount, "
			+ " sum(osf.arrears) as dayArrears, "
			+ " trunc(osf.fastmail_time) + 1 as daytime "
			+ " from biz_order_signfor osf "
			+ " where osf.order_status = '3' "
			+ " and osf.user_id = ?1 "
			+ " and osf.fastmail_time < trunc(sysdate - 1) "
			+ " group by trunc(osf.fastmail_time) "
			+ " union "
			+ " select count(osf.signid) as orderCount, "
			+ " sum(osf.actual_pay_num) as dayArrears, "
			+ " trunc(osf.fastmail_time) + 1 as daytime "
			+ " from biz_order_signfor osf "
			+ " where (osf.order_status = '0' or osf.order_status = '2') "
			+ " and osf.user_id = ?1 "
			+ " and osf.fastmail_time < trunc(sysdate - 1) "
			+ " group by trunc(osf.fastmail_time)) t "
			+ " group by t.daytime "
			+ " order by t.daytime desc) o "
			+ " left join (select count(*) as shopCount, t.fastmail_time "
			+ " from (select trunc(osr.fastmail_time) + 1 as fastmail_time, "
			+ " osr.shop_name "
			+ " from biz_order_signfor osr "
			+ " where osr.order_status = '3' "
			+ " and osr.fastmail_time < trunc(sysdate - 1) "
			+ " and osr.user_id = ?1 "
			+ " group by trunc(osr.fastmail_time), osr.shop_name "
			+ " union "
			+ " select trunc(osr.fastmail_time) + 1 as fastmail_time, "
			+ " osr.shop_name "
			+ " from biz_order_signfor osr "
			+ " where (osr.order_status = '0' or osr.order_status = '2') "
			+ " and osr.fastmail_time < trunc(sysdate - 1) "
			+ " and osr.user_id = ?1 "
			+ " group by trunc(osr.fastmail_time), osr.shop_name) t "
			+ " group by t.fastmail_time) os on os.fastmail_time = o.daytime "
			+ " order by o.daytime desc", nativeQuery = true)
  Long findBillHistoryConfluenceCount(String userId);


	 @Query("select o.shopName,o.orderNo,o.orderPayType,o.orderPrice,o.creatTime,o.arrears,o.billStatus,o.isPrimaryAccount,o.orderStatus,o.actualPayNum from OrderSignfor o  where o.userId= ?1 and o.fastmailTime = to_date(?2,'yyyy/MM/dd')-1  and ( o.orderStatus = '0' or  o.orderStatus = '2' or o.orderStatus = '3')")
	 Page<Object> findByUserIdAndCreatTime(String userId,String date, Pageable pageRequest);

	 Page<OrderSignfor> findAll(Specification<OrderSignfor> specification, Pageable pageRequest);

	 @Query("select o.overTime from OrderSignfor o where o.orderNo = ?1")
	  Object findOverTimeByOrderNo(String ordernum);
	 
	 @Query(value = " select (select sum(o.arrears)"
	           +   " from biz_order_signfor o "
	           +  " where o.user_id = ?1"
	           +    " and o.fastmail_time =  trunc(?2 - 1)"
	           +     " and o.order_status = '3') + "
	           + " (select sum(o.actual_pay_num) "
	                   +"        from biz_order_signfor o "
	                    +"      where o.user_id = ?1 "
	                    +    " and o.fastmail_time =  trunc(?2 - 1)"
	                      +"  and (o.order_status = '0' or o.order_status = '2')) as historyArrears"
	      + " from dual", nativeQuery = true)
	Float currentArrears(String userId,Date createTime);


  /*************************************************************/

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
                  "       (select TRUNC(SYSDATE - 4) + 20 / 24 FROM dual) and\n" +
                  "       (SELECT TRUNC(SYSDATE)+1-1/86400 FROM dual)\n" +
                  "   and bos.member_phone = ?1", nativeQuery = true)
  List<OrderSignfor> findByMemberPhoneAndCreatTime(String memberPhone);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE BIZ_ORDER_SIGNFOR o\n" +
					"SET o.ORDER_STATUS = 2,o.ORDER_PAY_TYPE = null, o.CUSTOM_SIGNFOR_TIME = null ,\n" +
					"o.CUSTOM_SIGNFOR_EXCEPTION = null ,o.CUSTOM_SIGNFOR_GEOPOINT = null \n" +
					"WHERE o.SIGNID = ?1",nativeQuery = true)
  int updateOrderForCashCancel(String SIGNID);



}
