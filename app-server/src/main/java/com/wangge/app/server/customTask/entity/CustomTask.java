package com.wangge.app.server.customTask.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.app.server.entity.Salesman;

/**
 * 本模块为自定义任务; 功能:任务保存时向手机端提供推送,并生成任务详情页面链接;手机端用web组件打开 推送可以全体广播或多人推送
 * 推送情况:1.全体推送可选个别区域不推送, 2.区域推送,个别用户不推送;
 * 
 * @author yangqc
 *
 */
@NamedEntityGraph(name = "customtask", attributeNodes = @NamedAttributeNode("salesmanSet"))
@Table(name = "sys_customtask")
@Entity
public class CustomTask {
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	private Long id;
	// 任务类型; 0:注册,1:售后,2:扣罚
	// @Enumerated(EnumType.ORDINAL)
	private int type;
	// 标题
	private String title;
	// 内容
	private String content;
	// 扣罚
	private int punishCount;
	@Temporal(TemporalType.DATE)
	private Date createTime = new Date();
	// 推送人员
	@ManyToMany(fetch = FetchType.LAZY)
	private Collection<Salesman> salesmanSet;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPunishCount() {
		return punishCount;
	}

	public void setPunishCount(int punishCount) {
		this.punishCount = punishCount;
	}

	public Collection<Salesman> getSalesmanSet() {
		return salesmanSet;
	}

	public void setSalesmanSet(Collection<Salesman> salesmanSet) {
		this.salesmanSet = salesmanSet;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public CustomTask() {
		super();
	}

}
