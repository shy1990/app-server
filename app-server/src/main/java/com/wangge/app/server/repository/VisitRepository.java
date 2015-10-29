package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.TaskVisit;

public interface VisitRepository extends JpaRepository<TaskVisit, Long>  {
	
	public TaskVisit findById(int id);
}
