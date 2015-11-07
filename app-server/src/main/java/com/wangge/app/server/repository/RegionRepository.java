package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Region;

public interface RegionRepository extends JpaRepository<Region, String>{

	public Region findById(String regionId);
	
	@Query("select  r.id,r.name from Region r where r.parent.id=? order by id desc")
	List<Object> findByParentId(String parentId);
	
	
}
