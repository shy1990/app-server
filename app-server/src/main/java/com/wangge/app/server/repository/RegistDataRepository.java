package com.wangge.app.server.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.RegistData;

public interface RegistDataRepository extends JpaRepository<RegistData, Long>{
	
}
