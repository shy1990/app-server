package com.wangge.app.server.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wangge.core.AuthAbale;

@Entity
@Table(schema = "YEWU", name = "T_USER")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements AuthAbale, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_ID")
	private String id;
	private String username;
	@JsonIgnore
	private String password;
	private String phone;

	public String getId() {
		return id;
	}

	public User() {
		super();
	}

	/**
	 * 
	 * @param id
	 *            用户编号
	 * @param username
	 *            登陆名
	 * @param password
	 *            登陆密码
	 */
	public User(String id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	/**
	 * 用户编号
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
