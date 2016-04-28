package com.wangge.app.server.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 
* @ClassName: SalesmanAddress
* @Description: TODO(业务员地址类，包括住址，物流点)
* @author A18ccms a18ccms_gmail_com
* @date 2016年4月8日 上午9:54:41
*
 */

@Entity
@Table(name = "SYS_SALESMAN_ADDRESS")
public class SalesmanAddress {
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  @Column(name = "ADDRESS_ID")
  private int id; //主键id
  
  private String userId;//业务员id
  @JsonFormat(pattern = "yyyy/MM/dd",timezone="GMT+8")
  private Date updateTime;//更新时间
  
  private String homePoint;//家庭地址坐标
  
  private String logisticsPoint1;//一号物流点坐标
  
  private String logisticsPoint2;//二号物流点坐标
  
  private String logisticsPoint3;//三号物流点坐标

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public String getHomePoint() {
    return homePoint;
  }

  public void setHomePoint(String homePoint) {
    this.homePoint = homePoint;
  }

  public String getLogisticsPoint1() {
    return logisticsPoint1;
  }

  public void setLogisticsPoint1(String logisticsPoint1) {
    this.logisticsPoint1 = logisticsPoint1;
  }

  public String getLogisticsPoint2() {
    return logisticsPoint2;
  }

  public void setLogisticsPoint2(String logisticsPoint2) {
    this.logisticsPoint2 = logisticsPoint2;
  }


  public String getLogisticsPoint3() {
    return logisticsPoint3;
  }

  public void setLogisticsPoint3(String logisticsPoint3) {
    this.logisticsPoint3 = logisticsPoint3;
  }


  
}
