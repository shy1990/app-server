package com.wangge.app.server.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.wangge.common.entity.Task;
@Entity
@Table(schema = "SANJI",name = "T_TASK_SAOJIE")
public class TaskSaojie extends  Task{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8954802303728756028L;
	
	private String salId;
	@OneToOne
	private TaskSaoJieRegion taskSaoJieRegion;
	

	public TaskSaoJieRegion getTaskSaoJieRegion() {
		return taskSaoJieRegion;
	}

	public void setTaskSaoJieRegion(TaskSaoJieRegion taskSaoJieRegion) {
		this.taskSaoJieRegion = taskSaoJieRegion;
	}

	public String getSalId() {
		return salId;
	}

	public void setSalId(String salId) {
		this.salId = salId;
	}
	
	

}
