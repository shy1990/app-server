package com.wangge.app.server.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.OilCostRecord;

public interface OilCostRecordRepository extends JpaRepository<OilCostRecord, Long>{

  OilCostRecord findByDateTimeAndUserId(Date dateTime, String userId);

   OilCostRecord findByUserId(String id) ;

  List<OilCostRecord> findByParentId(String userId);
}
