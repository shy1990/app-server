package com.wangge.app.server.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.wangge.common.entity.CustomRegion;
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
//	@OneToOne
//	private Region region;
	private String phone;
	@OneToOne
	private CustomRegion customRegion;


	public Salesman() {
		super();
	}

	public Salesman(String id, String username, String password, CustomRegion customRegion) {
		super(id, username, password);
		this.customRegion = customRegion;
	}
	

	public CustomRegion getCustomRegion() {
		return customRegion;
	}

	public void setCustomRegion(CustomRegion customRegion) {
		this.customRegion = customRegion;
	}

/*	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}*/

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
}
