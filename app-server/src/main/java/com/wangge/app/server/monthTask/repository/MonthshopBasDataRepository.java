package com.wangge.app.server.monthTask.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.app.server.entity.ApplyPrice;
import com.wangge.app.server.monthTask.entity.MonthshopBasData;

public interface MonthshopBasDataRepository extends JpaRepository<MonthshopBasData, Long>,JpaSpecificationExecutor<MonthshopBasData> {
	/**根据月份
	 * @param month
	 * @param regionid
	 * @param page
	 * @return
	 */
	public Page<MonthshopBasData> findByMonthAndRegionId(String month, String regionid, Pageable page);
	
	public MonthshopBasData findFirstByMonthAndRegistData_id(String month,Long shopid);
}
