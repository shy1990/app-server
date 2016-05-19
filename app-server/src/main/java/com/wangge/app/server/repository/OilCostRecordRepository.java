package com.wangge.app.server.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.OilCostRecord;

public interface OilCostRecordRepository extends JpaRepository<OilCostRecord, Long>{

  OilCostRecord findByDateTimeAndUserId(Date dateTime, String userId);

  // List<OilCostRecord> findByDateTimeAndParentId  (Date dateTime,String id) ;
   
  List<OilCostRecord> findByUserId(String userId);
  
  @Query("select o from OilCostRecord o where (o.userId=?1 or o.parentId=?1) and o.dateTime=?2 " )
  List<OilCostRecord> findYestdayOil(String userId,Date dateTime);
  
  @Query("select o from OilCostRecord o where (o.userId=?1 or o.parentId=?1) and o.dateTime between ?2 and ?3 " )
  List<OilCostRecord> findMonthOil(String userId,Date firsrtday,Date yesterday);
  
  List<OilCostRecord> findByDateTime(Date dateTime);
  
  @Query("select o from OilCostRecord o where o.userId=?1  and o.dateTime between ?2 and ?3 order by o.dateTime desc" )
  List<OilCostRecord> findHistoryDestOilRecord(String userId,Date beginDay,Date endDay);
  @Query("select o from OilCostRecord o where o.dateTime=?1  and o.userId = ?2  order by o.dateTime desc" )
  List<OilCostRecord>  findByDateTimeAndParentId(Date dateTime, String userId);
  
  
}
