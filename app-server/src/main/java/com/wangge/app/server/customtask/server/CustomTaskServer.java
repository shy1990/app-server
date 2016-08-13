package com.wangge.app.server.customtask.server;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.wangge.app.server.customtask.entity.CustomMessages;
import com.wangge.app.server.customtask.entity.CustomTask;


public interface CustomTaskServer {
	/**
	 * 获取分页数据
	 * 
	 * @param salesmanId
	 * @param page
	 * @return
	 */
	public Map<String, Object> getList(String salesmanId, Pageable page);



	/**将一个任务设置为已读
	 * @param customTask
	 * @param salesmanId 
	 */
	public void updateStatus(CustomTask customTask, String salesmanId);

	/**保存一条消息记录
	 * @param message
	 */
	public void saveMessage(CustomMessages message);

	/**查找customTask信息和其相关的消息记录
	 * @param customTask
	 * @param salesmanId
	 * @return
	 */
	public Map<String,Object> findCustomTask(CustomTask customTask, String salesmanId);

	/**查找与自定义任务相关的
	 * @param customTask
	 * @param salesmanId
	 * @return
	 */
	List<CustomMessages> findMessageList(CustomTask customTask, String salesmanId);
}
