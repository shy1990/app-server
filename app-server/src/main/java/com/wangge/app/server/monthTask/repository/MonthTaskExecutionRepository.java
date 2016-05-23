package com.wangge.app.server.monthTask.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.monthTask.entity.MonthTaskExecution;


public interface MonthTaskExecutionRepository
		extends JpaRepository< MonthTaskExecution, Long>{
	@EntityGraph("monthExecution.graph")
	List<MonthTaskExecution> findByTaskmonthAndRegistData_idOrderByTimeDesc(String taskMonth, Long memberId);
}
