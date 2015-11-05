package com.wangge.app.server.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * * 业务考核
 */
public class Exam  implements Serializable{
	
	private String username;//考核人
	private String stage;//考核阶段
	private String rate ;//已完成所占百分比
	@Temporal(TemporalType.TIMESTAMP)
	private Date beginDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	private String kpiNum;//目标数
	private String doneNum;//完成数
	private Map<String,Integer> map;    //提货商家及数量
	private String remark;//备注说明
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getKpiNum() {
		return kpiNum;
	}
	public void setKpiNum(String kpiNum) {
		this.kpiNum = kpiNum;
	}
	public String getDoneNum() {
		return doneNum;
	}
	public void setDoneNum(String doneNum) {
		this.doneNum = doneNum;
	}
	public Map<String, Integer> getMap() {
		return map;
	}
	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
