package com.wangge.app.server.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.DataSaojie;
import com.wangge.common.entity.Region;

public interface DataSaojieRepository extends JpaRepository<DataSaojie, String>{
	List<DataSaojie> findByTaskRegionsIn(Region region);

}
