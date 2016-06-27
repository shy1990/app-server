package com.wangge.app.server.customTask.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

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
}
