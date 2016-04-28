package com.wangge.app.server.repositoryimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

@Service
public class RegionImpl {
  @PersistenceContext  
  private EntityManager em; 
  
  public List<String> findParentIds(String regionId){
    // o.ship_status !=0  and 
    String sql = "select region_id from (select region_id from SYS_REGION START WITH region_id='"+regionId+"' CONNECT BY PRIOR   parent_id=region_id) where region_id not in ('"+regionId+"')";
    Query query =  em.createNativeQuery(sql);
    List<?> obj = query.getResultList();
    List<String> ids =new  ArrayList<String>();
    if(obj!=null && obj.size()>0){
      Iterator<?> it = obj.iterator();  
      while(it.hasNext()){
       // Object[] o = (Object[])it.next();
        String id =(String) it.next();
        
        ids.add(id);
      }
    }
      return ids;
  }
}
