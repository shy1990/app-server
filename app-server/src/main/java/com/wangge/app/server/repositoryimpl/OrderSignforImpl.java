package com.wangge.app.server.repositoryimpl;


import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.pojo.QueryResult;
import com.wangge.app.server.util.DateUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Repository
public class OrderSignforImpl {
  
  @PersistenceContext  
  private EntityManager em; 
  
  /**
   * 
  * @Title: getOrdersByMailNo 
  * @Description: TODO(根据物流单号和业务员手机号获取订单列表) 
  * @param @param fastmailNo
  * @param @param userPhone
  * @param @return    设定文件 
  * @return List<OrderSignfor>    返回类型 
  * @throws
   */
  public QueryResult<OrderSignfor>getOrdersByMailNo(String fastmailNo,String userPhone, int pageNo, int pageSize){
   String sql = "select  os.order_no,os.shop_name,os.creat_time,os.order_status,os.phone_count,os.order_price  from BIZ_ORDER_SIGNFOR os  where os.user_phone = '"+userPhone+"' and os.FASTMAIL_NO = '"+fastmailNo+"' order by os.signid desc";
  
   String countSql = "select  count(os.signid)    from BIZ_ORDER_SIGNFOR os  where os.user_phone = '"+userPhone+"' and os.FASTMAIL_NO = '"+fastmailNo+"' order by os.signid";
   Query query = em.createNativeQuery(sql);
   if(pageNo !=-1 && pageSize != -1)query.setFirstResult(pageNo*pageSize).setMaxResults(pageSize);
   BigDecimal a = (BigDecimal)  em.createNativeQuery(countSql).getSingleResult();
   List<OrderSignfor> olist = createOrderList(query.getResultList());
   QueryResult<OrderSignfor> qr = new QueryResult<OrderSignfor>();
   qr.setContent(olist);
   qr.setTotalPages(Long.parseLong(String.valueOf(a.intValue())),Long.parseLong(String.valueOf(pageSize)));
    return qr;
  }
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
       sql = "select  os.order_no,os.shop_name,os.creat_time,os.order_status,os.phone_count,os.order_price  from BIZ_ORDER_SIGNFOR os  where os.user_phone = '"+userPhone+"' order by os.creat_time desc";
       countSql = "select count(os.signid)  from BIZ_ORDER_SIGNFOR os  where os.user_phone = '"+userPhone+"' order by os.creat_time desc";
    }else{
       sql = "select  os.order_no,os.shop_name,os.creat_time,os.order_status,os.phone_count,os.order_price  from BIZ_ORDER_SIGNFOR os  where os.user_phone = '"+userPhone+"' and os.order_status in (2,3,4) order by os.creat_time desc";
       countSql = "select  count(os.signid)  from BIZ_ORDER_SIGNFOR os  where os.user_phone = '"+userPhone+"' and os.order_status in (2,3,4) order by os.creat_time desc";
    }
    
    Query query = em.createNativeQuery(sql);
    if(pageNo !=-1 && pageSize != -1)query.setFirstResult(pageNo*pageSize).setMaxResults(pageSize);
    BigDecimal a = (BigDecimal)  em.createNativeQuery(countSql).getSingleResult();
    
    List<OrderSignfor> olist = createOrderList(query.getResultList());
    QueryResult<OrderSignfor> qr = new QueryResult<OrderSignfor>();
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
   
     String sql = "select t.* from (select os.fastmail_no,os.yewu_signfor_time,os.fastmail_time, count(os.signid) as orderCount from biz_order_signfor os  where os.user_phone = '"+userPhone+"' and os.fastmail_time is not null  group by os.fastmail_no, os.yewu_signfor_time, os.fastmail_time ) t order by t.fastmail_time desc";
     String countSql = "select count(t.fastmail_no) from (select os.fastmail_no,os.yewu_signfor_time,os.fastmail_time, count(os.signid) as orderCount from biz_order_signfor os  where os.user_phone = '"+userPhone+"' and os.fastmail_time is not null group by os.fastmail_no, os.yewu_signfor_time, os.fastmail_time) t order by t.fastmail_time desc";
     Query query = em.createNativeQuery(sql);
     if(pageNo!=-1 && pageSize != -1)query.setFirstResult(pageNo*pageSize).setMaxResults(pageSize);
     BigDecimal a = (BigDecimal)  em.createNativeQuery(countSql).getSingleResult();
     List<OrderSignfor> olist = createOrderSignforList(query.getResultList());
     QueryResult<OrderSignfor> qr = new QueryResult<OrderSignfor>();
     qr.setContent(olist);
     qr.setTotalPages(Long.parseLong(String.valueOf(a.intValue())),Long.parseLong(String.valueOf(pageSize)));
     
    //Page<OrderSignfor> page = new PageImpl<OrderSignfor>(olist, new PageRequest(pageNo, pageSize), Long.parseLong(String.valueOf(query.getSingleResult())));
     return qr;
  }
  
  private List<OrderSignfor> createOrderList(List<?> olist){
    List<OrderSignfor> orderSignforList = new ArrayList<OrderSignfor>();
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
    if(olist!=null && olist.size()>0){
      Iterator<?> it = olist.iterator();  
      while(it.hasNext()){
        Object[] o = (Object[])it.next(); 
        OrderSignfor os = new OrderSignfor();
        os.setOrderNo((String) o[0]);
        os.setShopName(o[1]+"");
        try {
          os.setCreatTime(sdf.parse(o[2] + ""));
        } catch (ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        if (ObjectUtils.notEqual(o[3],null)){
          os.setOrderStatus(Integer.parseInt(o[3] + ""));
        }else {
          os.setOrderStatus(0);
        }
         os.setPhoneCount(Integer.parseInt(o[4] + ""));
         os.setOrderPrice((Float.valueOf(o[5] + "")));
        orderSignforList.add(os);
      }
      }
     return  orderSignforList;
  }
  
  private List<OrderSignfor> createOrderSignforList(List<?> olist){
    List<OrderSignfor> orderSignforList = new ArrayList<OrderSignfor>();
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
    if(olist!=null && olist.size()>0){
      Iterator<?> it = olist.iterator();  
      while(it.hasNext()){
        Object[] o = (Object[])it.next(); 
        if(o.length > 0 && o[0] != null){
          OrderSignfor os = new OrderSignfor();
          os.setFastmailNo(String.valueOf(o[0]+""));
          os.setOrderCount(Integer.parseInt(o[3]+""));
         
          try {
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
      }
  return  orderSignforList;
  }

  /**
   * 
   * @Description: 客户取消订单修改信息表类型
   * @param @param MessageType mt
   * @param @param String orderNum
   * @param @return   
   * @return String  
   * @throws
   * @author changjun
   * @date 2015年12月1日
   */
  @Transactional
  public String updateMessageType(int status,String orderNum){
    String sql = "update BIZ_ORDER_SIGNFOR set order_status= "+status+" where order_no = '"+orderNum+"'";
    Query query =  em.createNativeQuery(sql);
    return query.executeUpdate()>0?"suc":"false";
  }

  /**
   *
   * @Description: 客户取消订单修改信息表类型
   * @param @param MessageType mt
   * @param @param String orderNum
   * @param @return
   * @param abrogateTime:取消订单时间
   * @return String
   * @throws
   * @author changjun
   * @date 2015年12月1日
   */
  @Transactional
  public String updateMessageType(int status, String orderNum, Date abrogateTime){
    String updateTime = DateUtil.date2String(abrogateTime,"yyyy-MM-dd HH:mm:ss");
    String sql = "update BIZ_ORDER_SIGNFOR set order_status= "+status+",abrogate_time= to_date('" + updateTime +"','yyyy-mm-dd hh24:mi:ss') where order_no = '"+orderNum+"'";
    Query query =  em.createNativeQuery(sql);
    return query.executeUpdate()>0?"suc":"false";
  }
}
