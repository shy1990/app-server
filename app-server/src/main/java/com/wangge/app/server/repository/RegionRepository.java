package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Region;

public interface RegionRepository extends JpaRepository<Region, String> {
	
	public Region findById(String regionId);

	public Region findByNameLike(String regionName);
	@EntityGraph("region")
	public List<Region> findByParentId(String id);
}
