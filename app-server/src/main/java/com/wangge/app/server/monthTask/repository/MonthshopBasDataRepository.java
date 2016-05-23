package com.wangge.app.server.monthTask.repository;


import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.monthTask.entity.MonthshopBasData;

public interface MonthshopBasDataRepository
		extends JpaRepository<MonthshopBasData, Long>, JpaSpecificationExecutor<MonthshopBasData> {
	/**
	 * 根据月份
	 * 
	 * @param month
	 * @param regionid
	 * @param page
	 * @return
	 */
	@EntityGraph("monthShopBasData.graph")
	public Page<MonthshopBasData> findByMonthAndRegionId(String month, String regionid, Pageable page);

	@EntityGraph("monthShopBasData.graph")
	public MonthshopBasData findFirstByMonthAndRegistData_id(String month, Long shopid);

	@EntityGraph("task.graph")
	Page<MonthshopBasData> findAll(Specification<MonthshopBasData> spec, Pageable pageable);
	
	/**将monthtaskSub子表的pk同步到MonthshopBasData的fk TASK_ID中
	 * @return
	 */
	@Modifying
	@Transactional
	@Query(value="update sys_monthshop_basdata t set t.TASK_ID=(select s.id from sys_monthtask_sub s  where  t.id=s.data_id and rownum<2) where t.task_id is null",nativeQuery=true)
	int updateByMonthTaskSub();
}
