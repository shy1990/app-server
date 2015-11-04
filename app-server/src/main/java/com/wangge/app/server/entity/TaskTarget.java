package com.wangge.app.server.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(schema = "YEWU", name = "T_TASK_TARGET")
public class TaskTarget implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuidgenerator")
	@GenericGenerator(name = "uuidgenerator", strategy = "uuid")
	@Column(name = "TASK_TARGET_ID")
	private String id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TASK_ID")
	private Task task;
	private String name;
	
	@Column(name = "OPTION_")
	private Boolean option;

	private float value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getOption() {
		return option;
	}

	public void setOption(Boolean option) {
		this.option = option;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public TaskTarget() {
		super();
	}

	public TaskTarget(String name, Boolean option, float value) {
		super();
		this.name = name;
		this.option = option;
		this.value = value;
	}

}
