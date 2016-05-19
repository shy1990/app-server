package com.wangge.app.server.monthTask.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sys_monthtask_main")
public class MonthTask implements Serializable {
	/**
	* 
	 */
	private static final long serialVersionUID = 101L;
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	private long id;
	private String regionid;
	private String month;
	private String agentid;
	private Integer tal15goal;
	private Integer tal15Done;
	private Integer tal10goal;
	private Integer tal10done;
	private Integer tal7goal;
	private Integer tal7done;
	private Integer tal4goal;
	private Integer tal4done;
	private Integer tal20goal;
	private Integer tal20done;
	private Integer tal20set;
	private Integer tal15set;
	private Integer tal10set;
	private Integer tal7set;
	private Integer tal4set;
	// 状态0未发布,1已发布
	private Integer status;
	// 罚款数
	private Integer punishrate;
	@Transient
	private Set<MonthTaskSub> subSet = new HashSet<MonthTaskSub>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTown() {
		return regionid;
	}

	public void setTown(String regionid) {
		this.regionid = regionid;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}


	public Integer getTal15goal() {
		return tal15goal;
	}

	public void setTal15goal(Integer tal15goal) {
		this.tal15goal = tal15goal;
	}

	public Integer getTal15Done() {
		return tal15Done;
	}

	public void setTal15Done(Integer tal15Done) {
		this.tal15Done = tal15Done;
	}

	public Integer getTal10goal() {
		return tal10goal;
	}

	public void setTal10goal(Integer tal10goal) {
		this.tal10goal = tal10goal;
	}

	public Integer getTal10done() {
		return tal10done;
	}

	public void setTal10done(Integer tal10done) {
		this.tal10done = tal10done;
	}

	public Integer getTal7goal() {
		return tal7goal;
	}

	public void setTal7goal(Integer tal7goal) {
		this.tal7goal = tal7goal;
	}

	public Integer getTal7done() {
		return tal7done;
	}

	public void setTal7done(Integer tal7done) {
		this.tal7done = tal7done;
	}

	public Integer getTal4goal() {
		return tal4goal;
	}

	public void setTal4goal(Integer tal4goal) {
		this.tal4goal = tal4goal;
	}

	public Integer getTal4done() {
		return tal4done;
	}

	public void setTal4done(Integer tal4done) {
		this.tal4done = tal4done;
	}

	public Integer getTal20goal() {
		return tal20goal;
	}

	public void setTal20goal(Integer tal20goal) {
		this.tal20goal = tal20goal;
	}

	public Integer getTal20done() {
		return tal20done;
	}

	public void setTal20done(Integer tal20done) {
		this.tal20done = tal20done;
	}

	public Integer getTal20set() {
		return tal20set;
	}

	public void setTal20set(Integer tal20set) {
		this.tal20set = tal20set;
	}

	public Integer getTal15set() {
		return tal15set;
	}

	public void setTal15set(Integer tal15set) {
		this.tal15set = tal15set;
	}

	public Integer getTal10set() {
		return tal10set;
	}

	public void setTal10set(Integer tal10set) {
		this.tal10set = tal10set;
	}

	public Integer getTal7set() {
		return tal7set;
	}

	public void setTal7set(Integer tal7set) {
		this.tal7set = tal7set;
	}

	public Integer getTal4set() {
		return tal4set;
	}

	public void setTal4set(Integer tal4set) {
		this.tal4set = tal4set;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPunishrate() {
		return punishrate;
	}

	public void setPunishrate(Integer punishrate) {
		this.punishrate = punishrate;
	}

	public MonthTask(String regionid, String month, String agentid, Integer tal15goal, Integer tal10goal,
			Integer tal7goal, Integer tal4goal, Integer tal20goal) {
		super();
		this.regionid = regionid;
		this.month = month;
		this.agentid = agentid;
		this.tal15goal = tal15goal;
		this.tal10goal = tal10goal;
		this.tal7goal = tal7goal;
		this.tal4goal = tal4goal;
		this.tal20goal = tal20goal;
	}

	public MonthTask() {
	}

	public Set<MonthTaskSub> getSubSet() {
		return subSet;
	}

	public void setSubSet(Set<MonthTaskSub> subSet) {
		this.subSet = subSet;
	}
}
