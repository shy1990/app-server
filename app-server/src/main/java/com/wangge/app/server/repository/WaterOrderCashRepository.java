package com.wangge.app.server.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.app.server.entity.WaterOrderCash;
import org.springframework.data.jpa.repository.Query;

public interface WaterOrderCashRepository extends JpaRepository<WaterOrderCash, String>,
JpaSpecificationExecutor<WaterOrderCash>{

  List<WaterOrderCash> findByUserId(String userId);
  
  Page<WaterOrderCash> findByUserId(String userId,Pageable pageable);

	@Query("SELECT count(1) FROM WaterOrderCash woc where woc.userId= ?1 and woc.createDate >= TO_DATE(?2,'yyyy-mm-dd hh24:mi:ss') and woc.createDate <= TO_DATE(?3,'yyyy-mm-dd hh24:mi:ss')")
	Long countByUserIdAndCreateDate(String userId, String startDate,String endDate);
  
}
