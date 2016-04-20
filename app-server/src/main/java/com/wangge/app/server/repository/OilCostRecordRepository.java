package com.wangge.app.server.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.OilCostRecord;

public interface OilCostRecordRepository extends JpaRepository<OilCostRecord, Long>{

  OilCostRecord findByDateTimeAndUserId(Date dateTime, String userId);

   OilCostRecord findByUserId(String id) ;

  List<OilCostRecord> findByParentId(String userId);
  
  @Query("select o from OilCostRecord o where o.userId=?1 or o.parentId and dateTime=?2 " )
  List<OilCostRecord> findYestdayOil(String userId,Date dateTime);
}
