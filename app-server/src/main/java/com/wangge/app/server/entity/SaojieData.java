package com.wangge.app.server.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 扫街数据
 * 
 * @author wujiming
 *
 */
@Entity
@Table(schema = "SANJI", name = "T_DATA_SAOJIE")
@SequenceGenerator(schema="SANJI",name="SEQ_SAOJIE_DATA")
public class SaojieData implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_SAOJIE_DATA")
	private Long id;
	private String name;
	private String remark;
	private String imageUrl;
	private String coordinate;
	
	private String taskId;
	
	

	public SaojieData() {
		super();
	}
	
	

	public SaojieData(String name, String coordinate) {
		super();
		this.name = name;
		this.coordinate = coordinate;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	
}
