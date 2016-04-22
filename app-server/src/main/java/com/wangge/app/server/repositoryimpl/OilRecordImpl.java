package com.wangge.app.server.repositoryimpl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.wangge.app.server.entity.OilCostRecord;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.pojo.QueryResult;
import com.wangge.app.server.vo.OilCostRecordVo;
import com.wangge.app.server.vo.OrderPub;
@Service
public class OilRecordImpl {
  
  private static final Logger LOG = Logger.getLogger(OilRecordImpl.class);
  @PersistenceContext  
  private EntityManager em; 
  /**
   * 
  * @Title: getYesterydayOilRecord 
  * @Description: TODO(获取主账号昨日油补记录) 
  * @param @param userId
  * @param @return    设定文件 
  * @return OilCostRecord    返回类型 
  * @throws
   */
  public OilCostRecord getYesterydayOilRecord(String userId){
    String sql = "select t.track_id,t.parent_id,t.user_id,t.oil_record,t.distance,t.oil_cost,t.is_primary_account from biz_oil_cost_record t where t.user_id = '"+userId+"' and t.date_time = ( select to_date(to_char(sysdate-1,'yyyy/mm/dd'),'yyyy/mm/dd') from dual )";
    Query query =  em.createNativeQuery(sql);
    List<?> obj =  query.getResultList();
    if(obj!=null && obj.size()>0){
      Iterator<?> it = obj.iterator();  
      while(it.hasNext()){
        Object[] o = (Object[])it.next(); 
        OilCostRecord oilRecord = new OilCostRecord();
        oilRecord.setId(Integer.parseInt(o[0]+""));
        oilRecord.setParentId(o[1]+"");
        oilRecord.setUserId(o[2]+"");
        oilRecord.setOilRecord(o[3]+"");
        oilRecord.setDistance(Float.parseFloat(o[4]+""));
        oilRecord.setOilCost(Float.parseFloat(o[5]+""));
        oilRecord.setIsPrimaryAccount(Integer.parseInt(o[6]+""));
        return oilRecord;   
         }
      }
    return null;
  }
  
  
  /**
   * 
  * @Title: getChildYesterydayOilRecord 
  * @Description: TODO(获取主账号下所有子账号的昨日油补记录) 
  * @param @param userId
  * @param @return    设定文件 
  * @return List<OilCostRecord>    返回类型 
  * @throws
   */
  
  public List<OilCostRecord> getChildYesterydayOilRecord(String userId) {
    String sql = "select t.track_id,t.parent_id,t.user_id,t.oil_record,t.distance,t.oil_cost,t.is_primary_account from biz_oil_cost_record t where t.parent_id = '"+userId+"' and t.date_time = ( select to_date(to_char(sysdate-1,'yyyy/mm/dd'),'yyyy/mm/dd') from dual )";
    Query query =  em.createNativeQuery(sql);
    List<?> obj =  query.getResultList();
    List<OilCostRecord> oilRecordlist =new  ArrayList<OilCostRecord>();
    if(obj!=null && obj.size()>0){
      Iterator<?> it = obj.iterator();  
      while(it.hasNext()){
        Object[] o = (Object[])it.next(); 
        OilCostRecord oilRecord = new OilCostRecord();
        oilRecord.setId(Integer.parseInt(o[0]+""));
        oilRecord.setParentId(o[1]+"");
        oilRecord.setUserId(o[2]+"");
        oilRecord.setOilRecord(o[3]+"");
        oilRecord.setDistance(Float.parseFloat(o[4]+""));
        oilRecord.setOilCost(Float.parseFloat(o[5]+""));
        oilRecord.setIsPrimaryAccount(Integer.parseInt(o[6]+""));
        oilRecordlist.add(oilRecord);   
         }
      }
    return oilRecordlist;
  }
  
  public QueryResult<OilCostRecordVo>  getHistoryOilRecord(String userId,int pageNo,int pageSize) throws ParseException{
    String sql="select sum(t.oil_cost)as oilCost ,sum(t.distance)as distance,to_char(t.date_time,'yyyy/mm') as dateTime  from biz_oil_cost_record t where t.user_id = '"+userId+"' or t.parent_id = '"+userId+"' group by   to_char(t.date_time,'yyyy/mm')";
    String countSql = "select count(a.dateTime) from (+"
+"select to_char(t.date_time,'yyyy/mm') as dateTime  from biz_oil_cost_record t where t.user_id = '"+userId+"'and t.parent_id = 'C370105470' group by   to_char(t.date_time,'yyyy/mm') ) a";
    Query query = em.createNativeQuery(sql);
    if(pageNo !=-1 && pageSize != -1)query.setFirstResult(pageNo*pageSize).setMaxResults(pageSize);
    BigDecimal a = (BigDecimal)  em.createNativeQuery(countSql).getSingleResult();
    List<OilCostRecordVo> olist = createOilCostRecordList(query.getResultList());
    
    QueryResult<OilCostRecordVo> qr = new QueryResult<OilCostRecordVo>();
    qr.setContent(olist);
    qr.setTotalPages(Long.parseLong(String.valueOf(a.intValue())),Long.parseLong(String.valueOf(pageSize)));
     return qr;
  }
  
  
  
  private  List<OilCostRecordVo> createOilCostRecordList(List<?> olist) throws ParseException{
    List<OilCostRecordVo> voList = new ArrayList<OilCostRecordVo>();
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd");
   
    if(olist!=null && olist.size()>0){
      Iterator<?> it = olist.iterator();  
      while(it.hasNext()){
        Object[] o = (Object[])it.next(); 
        OilCostRecordVo vo = new OilCostRecordVo();
        vo.setDistance(o[0]+"");
        vo.setOilCost(o[1]+"");
      
        Calendar calendar = GregorianCalendar.getInstance();
        
        calendar.setTime(sdf.parse(o[2]+""));
        vo.setDateYear(String.valueOf(calendar.get(Calendar.YEAR)));
        vo.setDateMonth(String.valueOf(calendar.get(Calendar.MONTH)));
        
        voList.add(vo);    
        
      }
      }
     return  voList;
  }
  
  
}

