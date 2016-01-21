package com.wangge.app.server.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
/**
 * * 业务考核
 */
public class Exam  implements Serializable{
	//count(a.rid) as shopNum,a.rname areaName,a.rid areaId
	private String stage;//考核阶段
	private String shopRate ;//提货商家已完成所占百分比
	private String phoneRate ;//提货量已完成所占百分比
	private Date beginDate;
	private String  cycle; //周期
	

  private Integer goalShopNum;//活跃商家目标数
	private Integer  goalPhoneNum;//目标提货量
	private Integer doneShopNum;//完成数
	private Integer donePhoneNum;//完成数
	
	private Set<Town> town; //镇 
	
	public String getCycle() {
    return cycle;
  }
  public void setCycle(String cycle) {
    this.cycle = cycle;
  }
  public Set<Town> getTown() {
    return town;
  }
  public void setTown(Set<Town> town) {
    this.town = town;
  }
  public String getPhoneRate() {
    return phoneRate;
  }
  public String getShopRate() {
    return shopRate;
  }
  public void setShopRate(String shopRate) {
    this.shopRate = shopRate;
  }
  public void setPhoneRate(String phoneRate) {
    this.phoneRate = phoneRate;
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

  public Integer getGoalShopNum() {
    return goalShopNum;
  }
  public void setGoalShopNum(Integer goalShopNum) {
    this.goalShopNum = goalShopNum;
  }
  public Integer getGoalPhoneNum() {
    return goalPhoneNum;
  }
  public void setGoalPhoneNum(Integer goalPhoneNum) {
    this.goalPhoneNum = goalPhoneNum;
  }
  public Integer getDoneShopNum() {
    return doneShopNum;
  }
  public void setDoneShopNum(Integer doneShopNum) {
    this.doneShopNum = doneShopNum;
  }
  public Integer getDonePhoneNum() {
    return donePhoneNum;
  }
  public void setDonePhoneNum(Integer donePhoneNum) {
    this.donePhoneNum = donePhoneNum;
  }
	
	public static class Town{
	  private String areaName;
	  private String shopNum;
	  private String count;
    public String getAreaName() {
      return areaName;
    }
    public void setAreaName(String areaName) {
      this.areaName = areaName;
    }
    
    public String getShopNum() {
      return shopNum;
    }
    public void setShopNum(String shopNum) {
      this.shopNum = shopNum;
    }
    public String getCount() {
      return count;
    }
    public void setCount(String count) {
      this.count = count;
    }
	}
}
