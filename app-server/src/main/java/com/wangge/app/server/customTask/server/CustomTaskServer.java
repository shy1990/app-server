package com.wangge.app.server.customTask.server;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.wangge.app.server.customTask.entity.CustomTask;

public interface CustomTaskServer {
	/**
	 * 获取分页数据
	 * 
	 * @param salesmanId
	 * @param page
	 * @return
	 */
	public Map<String, Object> getList(String salesmanId, Pageable page);

	/**
	 * 保存自定义任务
	 * 
	 * @param customTask
	 */
	public void save(CustomTask customTask);
}
