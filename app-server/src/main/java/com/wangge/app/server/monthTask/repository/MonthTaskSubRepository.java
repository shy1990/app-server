package com.wangge.app.server.monthTask.repository;

import java.beans.Transient;

import javax.persistence.NamedEntityGraph;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wangge.app.server.monthTask.entity.MonthTaskSub;
import com.wangge.app.server.monthTask.entity.MonthshopBasData;

public interface MonthTaskSubRepository
		extends JpaRepository<MonthTaskSub, Long>, JpaSpecificationExecutor<MonthTaskSub> {
	@EntityGraph("monthTaskSub.monthsd")
	MonthTaskSub findFirstByMonthsd_id(Long id);

	@EntityGraph("monthTaskSub.monthsd")
	MonthTaskSub findFirstByMonthsd_RegistData_IdAndMonthsd_Month(Long shopId, String month);

	/*
	 * 更新数据库中延期的情况
	 */
	@Modifying
	@Transient
	@Query(value = "update   sys_monthtask_sub t set t.delay=1 where  (t.goal-t.done+3)> to_char(add_months(to_date(to_char(sysdate,'yyyy-mm'),'yyyy-mm'),1)-sysdate) and  t.delay=0", nativeQuery = true)
	void updatebyDelay();
}
