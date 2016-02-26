package com.wangge.app.server.repositoryimpl;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
@Repository
public class ActiveImpl {

  @PersistenceContext  
  private EntityManager em; 
  /**
   * 
  * @Title: examTwiceShopNum 
  * @Description: TODO(请求区域二次提货量店铺数量) 
  * @param @param area
  * @param @return    设定文件 
  * @return Double    返回类型 
  * @throws
   */
  public  Double examTwiceShopNum(String area){
    String sql = "select count(*) from sj_yewu.BIZ_ACTIVE c where c.n > =2 and c.member_id in  (select rd.member_id from biz_registdata rd    where rd.region_id  = '"+area+"')";
    Query query =  em.createNativeQuery(sql);
    Object s =   query.getSingleResult(); 
    return Double.parseDouble(s.toString()); 
  }
  /**
   * 
  * @Title: examOneceShopNum 
  * @Description: TODO(请求区域一次提货量店铺数量) 
  * @param @param area
  * @param @return    设定文件 
  * @return Double    返回类型 
  * @throws
   */
  public Double examOneceShopNum(String area) {
    String sql = "select count(*) from sj_yewu.BIZ_ACTIVE c where c.n> =1 and c.member_id in  (select rd.member_id from biz_registdata rd    where rd.region_id  = '"+area+"')";
    Query query =  em.createNativeQuery(sql);
    Object s =  query.getSingleResult(); 
    return  Double.parseDouble(s.toString());
  }
  /**
   * 
  * @Title: examTwiceShop 
  * @Description: TODO(店铺二次提货量) 
  * @param @param mobile
  * @param @return    设定文件 
  * @return int    返回类型 
  * @throws
   */
  public  Double examTwiceShop(String memberId){
    String sql = "select count(*) from sj_yewu.BIZ_ACTIVE c where c.n > =2 and c.member_id = '"+memberId+"'";
    Query query =  em.createNativeQuery(sql);
    Object s =   query.getSingleResult(); 
    return  Double.parseDouble(s.toString());
  }
/**
 * 
* @Title: examOneceShop 
* @Description: TODO(店铺一次提货量) 
* @param @param mobile
* @param @return    设定文件 
* @return int    返回类型 
* @throws
 */
  public Double examOneceShop(String memberId) {
    String sql = "select count(*) from sj_yewu.BIZ_ACTIVE c where c.n> =1 and c.member_id in  '"+memberId+"'";
    Query query =  em.createNativeQuery(sql);
    Object s =  query.getSingleResult(); 
    return  Double.parseDouble(s.toString());
  }
}
