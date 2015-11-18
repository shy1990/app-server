package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.common.entity.Region;

public interface RegionRepository extends JpaRepository<Region, String>{

	public Region findById(String regionId);
	
	@Query("select  r.id,r.name from Region r where r.parent.id=? order by id desc")
	List<Object> findByParentId(String parentId);
	
	@Query("select   r.id,r.name,r.coordinates,r.parent.id from Region r where r.parent.id=? order by id desc")
	List<Region> findCustomRegiond(String parentId);
	
}
