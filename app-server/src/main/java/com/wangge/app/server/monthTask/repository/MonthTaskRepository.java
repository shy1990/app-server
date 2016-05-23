package com.wangge.app.server.monthTask.repository;


import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.monthTask.entity.MonthTask;


public interface MonthTaskRepository extends JpaRepository<MonthTask, Long> {
	
	@Query(" select m  from MonthTask m where m.month=?1 and m.agentid=?2")
	MonthTask findFirstByMonth(String month,String agentid);
}
