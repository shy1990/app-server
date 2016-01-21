package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Regist;
import com.wangge.app.server.entity.Salesman;
import com.wangge.common.entity.Region;


public interface RegistRepository extends JpaRepository<Regist, Long> {
	
	/*@Query("select s.id,s.name,s.description,s.expiredTime,s.salesman.user.nickname,s.status,s.minValue from Regist s")
	List<Regist> findAllRegist();*/
	List<Regist> findBySalesman(Salesman salesman);
	
	Regist findByRegion(Region region);
	
	
	
}
