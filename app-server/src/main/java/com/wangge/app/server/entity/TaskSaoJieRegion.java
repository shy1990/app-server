package com.wangge.app.server.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "SANJI",name = "T_TASK_SAOJIE_REGION")
public class TaskSaoJieRegion {
	@Id
	private String reginId;
	private String taskId;
	
	public String getReginId() {
		return reginId;
	}
	public void setReginId(String reginId) {
		this.reginId = reginId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	
	
	

}
