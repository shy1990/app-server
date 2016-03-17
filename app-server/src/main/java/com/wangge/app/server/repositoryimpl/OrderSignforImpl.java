package com.wangge.app.server.repositoryimpl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.activemq.filter.function.inListFunction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.pojo.QueryResult;
import com.wangge.app.server.vo.OrderPub;
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
    String sql = ""; 
    if("0".equals(type)){
       sql = "select  os.order_no,os.shop_name,os.creat_time,os.order_status,os.phone_count,os.order_price  from BIZ_ORDER_SIGNFOR os  where os.user_phone = '"+userPhone+"' order by os.signid";
    }else{
       sql = "select  os.order_no,os.shop_name,os.creat_time,os.order_status,os.phone_count,os.order_price  from BIZ_ORDER_SIGNFOR os  where os.user_phone = '"+userPhone+"' and os.order_status = 2 order by os.signid";
    }
    
    Query query = em.createNativeQuery(sql);
    query.setFirstResult(pageNo);
    query.setMaxResults(pageSize);
    
    List<OrderSignfor> olist = createOrderList(query.getResultList());
    QueryResult<OrderSignfor> qr = new QueryResult();
    qr.setContent(olist);
    qr.setTotalPages(olist.size());
    //Page<OrderSignfor> page = new PageImpl<OrderSignfor>(olist, new PageRequest(pageNo, pageSize), olist.size());
    return qr;
  }
  /**
   * @throws ParseException 
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
     String sql = "select t.* from (select os.fastmail_no,os.order_status, os.creat_time,count(os.signid) as orderCount from biz_order_signfor os  where os.user_phone = '"+userPhone+"'  group by os.fastmail_no, os.order_status, os.creat_time  having count(os.signid) > 1 ) t order by t.creat_time";
     Query query = em.createNativeQuery(sql);
     query.setFirstResult(pageNo);
     query.setMaxResults(pageSize);
     List<OrderSignfor> olist = createOrderSignforList(query.getResultList());
     QueryResult<OrderSignfor> qr = new QueryResult();
     qr.setContent(olist);
     qr.setTotalPages(olist.size());
     
   //  Page<OrderSignfor> page = new PageImpl<OrderSignfor>(olist, new PageRequest(pageNo, pageSize), olist.size());
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
       /*for(int i = 0;i<olist.size();i++){
         OrderSignfor o = new OrderSignfor();
         System.out.println("============"+String.valueOf(olist.get(0)));
         o.setOrderNo(String.valueOf(olist.get(0)));
         o.setShopName(String.valueOf(olist.get(1)));
         //o.setCreateTime(new Date(Long.parseLong(String.valueOf(olist.get(2))) * 1000));
        // o.setOrderStatus(Integer.parseInt(String.valueOf(olist.get(3))));
        // o.setPhoneCount(Integer.parseInt(String.valueOf(olist.get(4))));
        // o.setOrderPrice((Float.valueOf(olist.get(5)+"")));
         orderSignforList.add(o);
       }*/
     return  orderSignforList;
  }
  
  private List<OrderSignfor> createOrderSignforList(List olist){
    List<OrderSignfor> orderSignforList = new ArrayList<OrderSignfor>();
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
    if(olist!=null && olist.size()>0){
      Iterator it = olist.iterator();  
      while(it.hasNext()){
        Object[] o = (Object[])it.next(); 
        OrderSignfor os = new OrderSignfor();
        os.setFastmailNo(String.valueOf(o[0]+""));
        os.setOrderStatus(Integer.parseInt(o[1]+""));
        os.setOrderCount(Integer.parseInt(o[3]+""));
        try {
          os.setCreatTime( sdf.parse(o[2]+""));
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
