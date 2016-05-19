package com.wangge.app.server.monthTask.repository;

import javax.persistence.NamedEntityGraph;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wangge.app.server.monthTask.entity.MonthTaskSub;
import com.wangge.app.server.monthTask.entity.MonthshopBasData;

public interface MonthTaskSubRepository
		extends JpaRepository<MonthTaskSub, Long>, JpaSpecificationExecutor<MonthTaskSub> {
//	@EntityGraph("monthExecution.graph")
	MonthTaskSub findFirstByMonthsd_id(Long id);
//	@EntityGraph("monthExecution.graph")
	MonthTaskSub findFirstByMonthsd_RegistData_IdAndMonthsd_Month(Long shopId,String month);
}
