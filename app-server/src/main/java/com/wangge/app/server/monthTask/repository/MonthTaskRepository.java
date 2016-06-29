package com.wangge.app.server.monthTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.monthTask.entity.MonthTask;


public interface MonthTaskRepository extends JpaRepository<MonthTask, Long> {
	
	@Query(" select m  from MonthTask m where m.month=?1 and m.agentid=?2 and m.status=1 ")
	MonthTask findFirstByMonth(String month,String agentid);
}
