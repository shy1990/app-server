package com.wangge.app.server.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.SaojieData;
import com.wangge.common.entity.Region;

public interface SaojieDataRepository extends JpaRepository<SaojieData, Long>{
	List<SaojieData> findByRegion(Region region);

}
