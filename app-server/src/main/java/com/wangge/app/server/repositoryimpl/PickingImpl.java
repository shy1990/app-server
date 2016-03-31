package com.wangge.app.server.repositoryimpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
@Repository
public class PickingImpl {
  @PersistenceContext  
  private EntityManager em; 
  /**
   * 
  * @Title: examPickingNum 
  * @Description: TODO(当前请求日期到月初的所在区域的商家的提货量) 
  * @param @param area
  * @param @return    设定文件 
  * @return int    返回类型 
  * @throws
   */
  public  int examPickingNum(String memberId){
    String sql = "select sum(b.nums) as n from  biz_picking b where b.member_id =  '"+memberId+"'";
    Query query =  em.createNativeQuery(sql);
    Object s =   query.getSingleResult(); 
    return  s != null ? Integer.parseInt(s.toString()) : 0; 
  }
}
