package com.wangge.app.server.monthTask.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.wangge.app.server.monthTask.entity.MonthTaskSub;

public interface MonthTaskServive {

	/**
	 * 通过代理商得到其下月的任务信息
	 * 
	 * @param userid
	 * @return
	 */
	ResponseEntity<Map<String, Object>> findTask(String userid);

	/**
	 * 得到一个县级区域下的所有镇
	 * 
	 * @param regionId
	 * @return
	 */
	ResponseEntity<Map<String, Object>> findRegion(String regionId);

	/**查询一个县区域下所有店铺的历史消费信息
	 * @param level  消费水平
	 * @param pickupDayType  查询关键字
	 * @param page 
	 * @return
	 */

	ResponseEntity<Map<String, Object>> findShopBy(Map<String, Object> searchParams,
			Pageable pageRequest);

	/**保存店铺任务实体
	 * @param talMap
	 */
	void save(Map<String, Object> talMap);

	/**根据区域和其他条件查找子任务
	 * @param searchParams
	 * @param pageRequest
	 * @return
	 * @throws Exception 
	 */
	Map<String, Object> findTask(Map<String, Object> searchParams, Pageable pageRequest) throws Exception;

	Map<String, Object> findExecution(Long shopId);
}
