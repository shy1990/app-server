package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wangge.app.server.entity.Regist;
import com.wangge.app.server.entity.Salesman;
import com.wangge.common.entity.Region;


public interface RegistRepository extends JpaRepository<Regist, Long> {
	
	List<Regist> findBySalesman(Salesman salesman);
	
	Regist findByRegion(Region region);
	
}
