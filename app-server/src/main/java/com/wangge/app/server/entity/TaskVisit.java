package com.wangge.app.server.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.wangge.common.entity.User;

/**
 * 拜访任务
 * @author peter
 *
 */
@Entity
@Table(schema = "SANJI", name = "T_TASK_VISIT")
public class TaskVisit extends User{
	private static final long serialVersionUID = 1L;
	
	private int id; //任务id
//	@OneToOne
//	private String username; //业务员
//	private String member; //商城用户名
	private String image_url; //图片链接
	private String point; //坐标
	private String remark; //备注
	private Date visit_time; //拜访时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/*public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}*/
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getVisit_time() {
		return visit_time;
	}
	public void setVisit_time(Date visit_time) {
		this.visit_time = visit_time;
	}
	
}
