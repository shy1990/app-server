package com.wangge.app.server.monthTask.service;

import java.lang.reflect.InvocationTargetException;
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

	/**
	 * 查询一个县区域下所有店铺的历史消费信息
	 * 
	 * @param level
	 *            消费水平
	 * @param pickupDayType
	 *            查询关键字
	 * @param page
	 * @return
	 */

	ResponseEntity<Map<String, Object>> findShopBy(Map<String, Object> searchParams, Pageable pageRequest);

	/**
	 * 保存店铺任务实体
	 * 
	 * @param talMap
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws NumberFormatException 
	 * @throws NoSuchFieldException 
	 */
	void save(Map<String, Object> talMap) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException;

	/**
	 * 根据区域和其他条件查找子任务
	 * 
	 * @param searchParams
	 * @param pageRequest
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> findTask(Map<String, Object> searchParams, Pageable pageRequest) throws Exception;

	/**
	 * 通过shopId查找本月任务执行详情
	 * 
	 * @param shopId
	 * @return
	 */
	Map<String, Object> findExecution(Long shopId);

	/**记录各种任务执行细节,每天一次;记录到具体拜访表和子任务表里
	 * @param shopId 店铺id
	 * @param action 执行动作,如主动拜访,送货签收,店铺注册
	 */
	void saveExecution(Long shopId, String action);
}
