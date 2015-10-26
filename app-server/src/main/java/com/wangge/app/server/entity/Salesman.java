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
	private String regionId;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	
	
	
}
