package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.OilParameters;
import com.wangge.app.server.entity.Region;

public interface OilParametersRepository  extends JpaRepository<OilParameters, Long>{
 
  @Query("select oil from OilParameters oil where oil.regionId = ?1")
  OilParameters findByRegionId(String regionId);
  @Query("select oil from OilParameters oil where oil.regionId = '0'")
  OilParameters findOilParameters();
  
  List<OilParameters> findByRegionIdIn(List<String> list);

  
  
  
  
}
