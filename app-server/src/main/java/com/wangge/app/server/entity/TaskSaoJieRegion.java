package com.wangge.app.server.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(schema = "SANJI",name = "T_TASK_SAOJIE_REGION")
public class TaskSaoJieRegion {
	@Id
	private String reginId;
	@JoinColumn(name = "TASK_ID")
	private SaojieTask task;
	
	public String getReginId() {
		return reginId;
	}
	public void setReginId(String reginId) {
		this.reginId = reginId;
	}
	public SaojieTask getTask() {
		return task;
	}
	public void setTask(SaojieTask task) {
		this.task = task;
	}
	
	
	
	

}
