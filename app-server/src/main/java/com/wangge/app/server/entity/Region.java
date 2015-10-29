package com.wangge.app.server.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.wangge.common.entity.User;

/**
 * 区域
 * @author peter
 *
 */
@Entity
@Table(schema = "SANJI", name = "T_REGION")
public class Region extends User{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="parent_id")
	private Region region;
	private String name;
	private String type;
	private UserMember userMember;
	
	@OneToOne(mappedBy="Region")
	public UserMember getUserMember() {
		return userMember;
	}
	public void setUserMember(UserMember userMember) {
		this.userMember = userMember;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
