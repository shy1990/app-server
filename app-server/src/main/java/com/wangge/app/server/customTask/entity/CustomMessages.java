package com.wangge.app.server.customTask.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.app.server.util.DateUtil;


@Entity
@Table(name = "sys_custom_message")
public class CustomMessages {
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	private Long id;
	// 自定义任务id
	private long customtaskId;
	// 业务员id
	private String salesmanId;
	// 谈话内容
	private String content;
	// 0:消息发起者;1业务员
	private int roletype;
	private Date time=new Date();
	// 信息读取状态,0未读,1已读
	private int status;
	
	@Transient
	private  String ctime;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getCustomtaskId() {
		return customtaskId;
	}

	public void setCustomtaskId(long customtaskId) {
		this.customtaskId = customtaskId;
	}

	public String getSalesmanId() {
		return salesmanId;
	}

	public void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRoletype() {
		return roletype;
	}

	public void setRoletype(int roletype) {
		this.roletype = roletype;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	
	public void setCtime(){
		this.ctime=DateUtil.date2String(this.time, "MM-dd HH:mm");
	}
	public CustomMessages() {
	}

}
