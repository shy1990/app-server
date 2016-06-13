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

	/*目标数-已完成+3>=本月剩余时间
	 * 更新数据库中延期的情况
	 */
	@Modifying
	@Transactional
	@Query(value = "update  sys_monthtask_sub t set t.delay=1 where  (t.goal-t.done+3)>= to_char(add_months(to_date(to_char(sysdate,'yyyy-mm'),'yyyy-mm'),1)-sysdate) and  t.delay=0", nativeQuery = true)
	void updatebyDelay();
}
