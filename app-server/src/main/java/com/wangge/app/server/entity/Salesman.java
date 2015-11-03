package com.wangge.app.server.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.wangge.common.entity.Region;
import com.wangge.common.entity.User;

/**
 * 业务员
 * 
 * @author wujiming
 *
 */
@Entity
@Table(schema = "SANJI", name = "T_SALESMAN")
public class Salesman extends User {
	private static final long serialVersionUID = 1L;
	@OneToOne
	private Region region;
	private String phone;
	@OneToMany(mappedBy="taskSaojie")
	private Set<TaskSaojie>  taskSaojie;

	public Salesman() {
		super();
	}

	public Salesman(String id, String username, String password, Region region) {
		super(id, username, password);
		this.region = region;
	}
	
	public Set<TaskSaojie> getTaskSaojie() {
		return taskSaojie;
	}

	public void setTaskSaojie(Set<TaskSaojie> taskSaojie) {
		this.taskSaojie = taskSaojie;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
