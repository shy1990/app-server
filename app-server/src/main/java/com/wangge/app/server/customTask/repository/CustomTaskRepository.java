package com.wangge.app.server.customTask.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.customTask.entity.CustomTask;

public interface CustomTaskRepository extends JpaRepository<CustomTask, Long> {
//	@Query("select c from CustomTask c where   c.salesmanSet.id  =?1 ")
	Page<CustomTask> findBysalesmanSetIdOrderByCreateTimeDesc(String salesmanId, Pageable page);

}
