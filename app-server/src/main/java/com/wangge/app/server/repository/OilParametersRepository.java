package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.OilParameters;

public interface OilParametersRepository  extends JpaRepository<OilParameters, Long>{
 
  @Query("select oil from OilParameters oil where oil.regionId =?1 or oil.regionId is null")
  OilParameters findByRegionId(String regionId);

  
  
  
  
}
