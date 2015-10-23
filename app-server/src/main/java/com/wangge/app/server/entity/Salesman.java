package com.wangge.app.server.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.wangge.common.entity.User;

/**
 * 业务员
 * @author wujiming
 *
 */
@Entity
@Table(schema = "SANJI", name = "T_SALESMAN")
public class Salesman extends User{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
