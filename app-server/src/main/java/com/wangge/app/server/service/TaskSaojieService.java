package com.wangge.app.server.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.repository.TaskRepository;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class TaskSaojieService {


	@Resource
	private TaskRepository taskRepository;

	public void addSaojieTask(Saojie saojie) {
		taskRepository.save(saojie);
	}
//
//
//	public List<TaskSaojie> findTaskSJbysalesman(Salesman salesman){
//		
//		return taskSaojieRepository.findBySalesman(salesman);
//	}
//	
//	public List<TaskSaojie> findBycreateBy(SalesmanManager salesmanManager){
//			
//		return taskSaojieRepository.searchsaojie(salesmanManager.getId());
//	}
//	
//	public String updateStatus(String salesname,String taskname){
//		
//		
//		return "OK";
//	}
//
//
//	public void saveTask(Task task){
//		taskRepository.save(task);
//	}
//	
//	public Task findByTaskId(String taskid) {
//		Task task = taskRepository.findOne(taskid);
//		
//		return task;
	}

