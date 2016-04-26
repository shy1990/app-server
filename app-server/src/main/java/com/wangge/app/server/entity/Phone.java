package com.wangge.app.server.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;



/**
 * 手机信息
 * 
 * @author stone
 *
 */

//@Entity
//@Table(name = "SYS_PHONE")
public class Phone extends AbstractPersistable<Long>{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	private Long id;
	@Column(name = "PHONE_MODEL")
	private String phoneModel;
	@Column(name = "PHONE_NUM")
	private String phoneNum;
	@Column(name = "PHONE_ACT_TIME")
	private String actTime;
	@Column(name = "PHONE_INT_TIME")
	private String intTime;
	@Column(name = "SERVER_TIME")
	private String serverTime;
	
	public Phone() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPhoneModel() {
		return phoneModel;
	}
	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getActTime() {
		return actTime;
	}
	public void setActTime(String actTime) {
		this.actTime = actTime;
	}
	public String getIntTime() {
		return intTime;
	}
	public void setIntTime(String intTime) {
		this.intTime = intTime;
	}
	public String getServerTime() {
		return serverTime;
	}
	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}
	
	
}
