package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Region;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Saojie;

public interface SaojieRepository extends JpaRepository<Saojie, Long> {
	List<Saojie> findBySalesman(Salesman salesman);
	Saojie findByParentIsNull();
	@Query("select s.id,s.name,s.description,s.expiredTime,s.salesman.user.nickname,s.status,s.minValue from Saojie s where s.parent.id is not null")
	List<Saojie> findAllSaojie();
	

	Saojie findByOrderAndSalesman(Integer id, Salesman salesman);

	@Query("from Saojie s where s.salesman.id =? and s.order=?")
	Saojie fingSaojie(String id,Integer order);
  Saojie findByRegionAndSalesman(Region region, Salesman salesman);

}
