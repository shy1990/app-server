package com.wangge.app.server.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wangge.app.server.entity.Region;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Task.TaskStatus;
import com.wangge.app.server.entity.TaskSaojie;
import com.wangge.app.server.repository.TaskSaojieRepository;

@Service
public class RegionService {
	@Autowired
	private TaskSaojieRepository taskSaojieRepository;

	/**
	 * 获取扫街区域信息
	 * 
	 * @param salesman
	 * @return Map<String, List<Region>> ,其中 key 为 can,cant.
	 */
	public Map<String, List<Region>> getSaojie(Salesman salesman) {
		Map<String, List<Region>> result = Maps.newHashMap();
		List<Region> can = Lists.newArrayList();
		List<Region> cant = Lists.newArrayList();
		List<TaskSaojie> saojieTasks = taskSaojieRepository.findBySalesman(salesman);
		// TODO 可用java8 stream过滤
		for (TaskSaojie taskSaojie : saojieTasks) {
			if (TaskStatus.PENDING.equals(taskSaojie.getStatus())) {
				can.addAll(taskSaojie.getRegions());
			} else {
				cant.addAll(taskSaojie.getRegions());
			}
		}
		result.put("can", can);
		result.put("cant", cant);
		return result;
	}

}
