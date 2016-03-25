package com.wangge.app.server.repositoryimpl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Repository;


@Repository
public class OrderSignforRepositoryImpl {
  @PersistenceContext  
  private EntityManager em;  
  /**
   * 
  * @Title: updateOrderSignforByOrderNo 
  * @Description: TODO(这里用一句话描述这个方法的作用) 
  * @param     设定文件 
  * @return void    返回类型 
  * @throws
   */
  @Transactional
  public String updateOrderSignforByOrderNo(String fastmailno,String orderno){
     String sql = "update  SJ_YEWU.BIZ_ORDER_SIGNFOR orderSignfor  set orderSignfor.FASTMAIL_NO = '"+fastmailno+"' where orderSignfor.ORDER_NO = '"+orderno+"'";
     Query query =  em.createNativeQuery(sql);
     return query.executeUpdate() > 0 ? "success":"false";
  }
}
