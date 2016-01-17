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
	//count(a.rid) as shopNum,a.rname areaName,a.rid areaId
	private String stage;//考核阶段
	private String rate ;//已完成所占百分比
	@Temporal(TemporalType.TIMESTAMP)
	private Date beginDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	private Integer kpiNum;//目标数
	private Integer doneNum;//完成数
	private Map<Object,Object> map;    //提货商家及数量
	private String remark;//备注说明
	
	
	
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

	public Integer getKpiNum() {
		return kpiNum;
	}
	public void setKpiNum(Integer kpiNum) {
		this.kpiNum = kpiNum;
	}
	public Integer getDoneNum() {
		return doneNum;
	}
	public void setDoneNum(Integer doneNum) {
		this.doneNum = doneNum;
	}
	public Map<Object, Object> getMap() {
		return map;
	}
	public void setMap(Map<Object, Object> map) {
		this.map = map;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
