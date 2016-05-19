package com.wangge.app.server.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Region;

public interface RegionRepository extends JpaRepository<Region, String> {
	public Region findById(String regionId);
	
	public Region findByNameLike(String regionName);
	
	public List<Region> findByParentId(String id);

	
}
