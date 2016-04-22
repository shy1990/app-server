package com.wangge.app.server.repositoryimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.wangge.app.server.entity.OilCostRecord;
import com.wangge.app.server.vo.OrderPub;
@Service
public class OilRecordImpl {
  
  private static final Logger LOG = Logger.getLogger(OilRecordImpl.class);
  @PersistenceContext  
  private EntityManager em; 
 
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
        oilRecord.setDistance(Long.parseLong(o[4]+""));
        oilRecord.setOilCost(Float.parseFloat(o[5]+""));
        oilRecord.setIsPrimaryAccount(Integer.parseInt(o[6]+""));
        return oilRecord;   
         }
      }
    return null;
  }
  
  
  
  
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
        oilRecord.setDistance(Long.parseLong(o[4]+""));
        oilRecord.setOilCost(Float.parseFloat(o[5]+""));
        oilRecord.setIsPrimaryAccount(Integer.parseInt(o[6]+""));
        oilRecordlist.add(oilRecord);   
         }
      }
    return oilRecordlist;
  }
}
