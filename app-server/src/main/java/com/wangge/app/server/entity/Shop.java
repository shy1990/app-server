package com.wangge.app.server.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

//@Entity
@Table(schema = "YEWU", name = "T_SHOP")
public class Shop implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "shop_id")
	private String id;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "saojie_data_id")
	private SaojieData dataSaojie;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TASK_ID")
	private Task task;

	private String name;
	private String consignee;
	private String consigneePhone;
	private Integer counterNumber;
	private Date createTime;
	private String coordinate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SaojieData getDataSaojie() {
		return dataSaojie;
	}
	public void setDataSaojie(SaojieData dataSaojie) {
		this.dataSaojie = dataSaojie;
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
	public Integer getCounterNumber() {
		return counterNumber;
	}
	public void setCounterNumber(Integer counterNumber) {
		this.counterNumber = counterNumber;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getConsigneePhone() {
		return consigneePhone;
	}
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	
	
}
