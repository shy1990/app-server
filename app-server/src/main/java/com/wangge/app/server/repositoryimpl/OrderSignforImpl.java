package com.wangge.app.server.repositoryimpl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.pojo.QueryResult;

@Repository
public class OrderSignforImpl {
  
  @PersistenceContext  
  private EntityManager em; 
  /**
   * @throws ParseException 
   * 
  * @Title: getOrderList 
  * @Description: TODO(这里用一句话描述这个方法的作用) 
  * @param @param userPhone
  * @param @param type
  * @param @param pageNo
  * @param @param pageSize
  * @param @return    设定文件 
  * @return QueryResult<OrderSignfor>    返回类型 
  * @throws
   */
  public QueryResult<OrderSignfor> getOrderList(String userPhone, String type, int pageNo, int pageSize){
    String sql = ""; String countSql = "";
    if("0".equals(type)){
       sql = "select  os.order_no,os.shop_name,os.creat_time,os.order_status,os.phone_count,os.order_price  from BIZ_ORDER_SIGNFOR os  where os.user_phone = '"+userPhone+"' order by os.signid";
       countSql = "select count(os.signid)  from BIZ_ORDER_SIGNFOR os  where os.user_phone = '"+userPhone+"' order by os.signid";
    }else{
       sql = "select  os.order_no,os.shop_name,os.creat_time,os.order_status,os.phone_count,os.order_price  from BIZ_ORDER_SIGNFOR os  where os.user_phone = '"+userPhone+"' and os.order_status in (2,3,4) order by os.signid";
       countSql = "select  count(os.signid)  from BIZ_ORDER_SIGNFOR os  where os.user_phone = '"+userPhone+"' and os.order_status in (2,3,4) order by os.signid";
    }
    
    Query query = em.createNativeQuery(sql);
    if(pageNo !=-1 && pageSize != -1)query.setFirstResult(pageNo*pageSize).setMaxResults(pageSize);
    BigDecimal a = (BigDecimal)  em.createNativeQuery(countSql).getSingleResult();
    
    List<OrderSignfor> olist = createOrderList(query.getResultList());
    QueryResult<OrderSignfor> qr = new QueryResult();
    qr.setContent(olist);
    qr.setTotalPages(Long.parseLong(String.valueOf(a.intValue())),Long.parseLong(String.valueOf(pageSize)));
   // Page<OrderSignfor> page = new PageImpl<OrderSignfor>(olist, new PageRequest(pageNo, pageSize), Long.parseLong(String.valueOf(a.intValue())));
    return qr;
  }
  /**
   * @throws ParseException 
   * 
   * 
  * @Title: getOrderSignforList 
  * @Description: TODO(这里用一句话描述这个方法的作用) 
  * @param @param userPhone
  * @param @param pageNo
  * @param @param pageSize
  * @param @return    设定文件 
  * @return QueryResult<OrderSignfor>    返回类型 
  * @throws
   */
  public QueryResult<OrderSignfor> getOrderSignforList(String userPhone, int pageNo, int pageSize) {
   
     String sql = "select t.* from (select os.fastmail_no,os.yewu_signfor_time,os.fastmail_time, count(os.signid) as orderCount from biz_order_signfor os  where os.user_phone = '"+userPhone+"'  group by os.fastmail_no, os.yewu_signfor_time, os.fastmail_time ) t order by t.fastmail_time";
     String countSql = "select count(t.fastmail_no) from (select os.fastmail_no,os.yewu_signfor_time,os.fastmail_time, count(os.signid) as orderCount from biz_order_signfor os  where os.user_phone = '"+userPhone+"'  group by os.fastmail_no, os.yewu_signfor_time, os.fastmail_time) t order by t.fastmail_time";
     Query query = em.createNativeQuery(sql);
     if(pageNo!=-1 && pageSize != -1)query.setFirstResult(pageNo*pageSize).setMaxResults(pageSize);
     BigDecimal a = (BigDecimal)  em.createNativeQuery(countSql).getSingleResult();
     List<OrderSignfor> olist = createOrderSignforList(query.getResultList());
     QueryResult<OrderSignfor> qr = new QueryResult();
     qr.setContent(olist);
     qr.setTotalPages(Long.parseLong(String.valueOf(a.intValue())),Long.parseLong(String.valueOf(pageSize)));
     
    //Page<OrderSignfor> page = new PageImpl<OrderSignfor>(olist, new PageRequest(pageNo, pageSize), Long.parseLong(String.valueOf(query.getSingleResult())));
     return qr;
  }
  
  private List<OrderSignfor> createOrderList(List olist){
    List<OrderSignfor> orderSignforList = new ArrayList<OrderSignfor>();
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
    if(olist!=null && olist.size()>0){
      Iterator it = olist.iterator();  
      while(it.hasNext()){
        Object[] o = (Object[])it.next(); 
        OrderSignfor os = new OrderSignfor();
        os.setOrderNo(o[0]+"");
        os.setShopName(o[1]+"");
        try {
          os.setCreatTime(sdf.parse(o[2]+""));
        } catch (ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
         os.setOrderStatus(Integer.parseInt(o[3]+""));
         os.setPhoneCount(Integer.parseInt(o[4]+""));
         os.setOrderPrice((Float.valueOf(o[5]+"")));
        orderSignforList.add(os);
      }
      }
     return  orderSignforList;
  }
  
  @SuppressWarnings("unused")
  private List<OrderSignfor> createOrderSignforList(List olist){
    List<OrderSignfor> orderSignforList = new ArrayList<OrderSignfor>();
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
    if(olist!=null && olist.size()>0){
      Iterator it = olist.iterator();  
      while(it.hasNext()){
        Object[] o = (Object[])it.next(); 
        OrderSignfor os = new OrderSignfor();
        os.setOrderCount(Integer.parseInt(o[3]+""));
       
        try {
          if(o[0] != null){
            os.setFastmailNo(String.valueOf(o[0]+""));
          }
         if(o[2] != null){
           os.setFastmailTime(sdf.parse(o[2]+""));
         }
         if(o[1] != null){
           os.setYewuSignforTime(sdf.parse(o[1]+""));
           os.setStatus(1);
         }else{
           os.setStatus(0);
         }
        } catch (ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
       
        orderSignforList.add(os);
      }
      }
  return  orderSignforList;
  }

}

