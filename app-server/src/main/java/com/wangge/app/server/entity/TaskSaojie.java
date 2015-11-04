package com.wangge.app.server.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 扫街任务
 * 
 * @author wujiming
 *
 */
@Entity
@Table(schema = "YEWU", name = "T_TASK_SAOJIE")
public class TaskSaojie extends Task {
	private static final long serialVersionUID = 1L;

	public TaskSaojie() {
		super();
	}
}
