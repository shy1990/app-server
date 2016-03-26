package com.wangge.app.server.repositoryimpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
@Repository
public class DateInterval {
  @PersistenceContext  
  private EntityManager em; 
  /**
   * 
  * @Title: examDateInterval 
  * @Description: TODO(当前请求与客户最后一次下单的时间差) 
  * @param @param memberId
  * @param @return    设定文件 
  * @return int    返回类型 
  * @throws
   */
  public  int examDateInterval(String memberId){
    String sql = "select floor(sysdate - (select   to_date((to_char((select t.createtime from biz_dateinterval t  where rownum = 1 and t.member_id = '"+memberId+"'),'yyyy/mm/dd')),'yyyy/mm/dd')   from dual) ) as d from dual";
    Query query =  em.createNativeQuery(sql);
    Object s =   query.getSingleResult(); 

    return s != null ? Integer.parseInt(s.toString()) : -1; 
  }
}
