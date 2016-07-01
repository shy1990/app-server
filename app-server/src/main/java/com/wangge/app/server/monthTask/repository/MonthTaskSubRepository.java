package com.wangge.app.server.monthTask.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.app.server.monthTask.entity.MonthTaskSub;

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
	@Transactional
	@Query(value = 
  "update sys_monthtask_sub t\n" +
      "   set t.delay = 1  where (t.goal - t.done + 3) >=\n" + 
      "       to_char(add_months(to_date(to_char(sysdate, 'yyyy-mm'), 'yyyy-mm'), 1) - sysdate)\n" + 
      "   and t.delay = 0    and exists (select 1 \n" + 
      "          from sys_monthshop_basdata d \n" + 
      "         where d.month = to_char(sysdate, 'yyyy-mm')\n" + 
      "           and t.data_id = d.id)", nativeQuery = true)
	void updatebyDelay();
}
