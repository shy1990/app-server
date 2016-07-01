package com.wangge.app.server.customTask.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.customTask.entity.CustomMessages;

public interface CustomMessagesRepository extends JpaRepository<CustomMessages, Long> {
	/**
	 * 通过自定义模块主键和业务员id来查找聊天记录
	 * 
	 * @param customtaskId
	 * @param salesmanId
	 * @return
	 */
	List<CustomMessages> findByCustomtaskIdAndSalesmanIdOrderByTimeAsc(Long customtaskId, String salesmanId);

	@Transactional
	@Modifying
	@Query("update CustomMessages m set m.status=1 where m.customtaskId =?1 and m.salesmanId=?2 and m.status=0 and m.roletype=0")
	int updateBySalesmainId(Long customtaskId, String salesmanId);
}
