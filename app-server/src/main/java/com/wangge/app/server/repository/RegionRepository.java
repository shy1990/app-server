package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Region;

public interface RegionRepository extends JpaRepository<Region, String>{

	public Region findById(String regionId);
}
