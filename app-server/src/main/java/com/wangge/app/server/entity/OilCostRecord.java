package com.wangge.app.server.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "BIZ_OIL_COST_RECORD")
public class OilCostRecord implements Serializable{

  /**
  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
  */
  
  private static final long serialVersionUID = 1L;
  @Id
  @GenericGenerator(name = "idgen",strategy = "increment")
  @GeneratedValue(generator="idgen")
  @Column(name = "TRACK_ID")
  private int id; //主键id
  private String userId;//业务id
  // private String Coordinates;//坐标集合
  @Temporal(TemporalType.DATE)
  private Date dateTime;//日期
  private int isPrimaryAccount;//是否主账号
  //private String regionIds;//regionId集合
  private Float oilCost;//油补的费用
  private Float  distance;//里程数
  
  @Lob  
  @Basic(fetch = FetchType.LAZY)  
  @Column(columnDefinition = "CLOB", name = "OIL_RECORD")
  private String  oilRecord;//Coordinates,regionIds，shopName等  油补 json串
  
  private String parentId;
  
  private String regionIds;
  
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
  
  public Date getDateTime() {
    return dateTime;
  }
  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }
  public int getIsPrimaryAccount() {
    return isPrimaryAccount;
  }
  public void setIsPrimaryAccount(int isPrimaryAccount) {
    this.isPrimaryAccount = isPrimaryAccount;
  }
  public Float getOilCost() {
    return oilCost;
  }
  public void setOilCost(Float oilCost) {
    this.oilCost = oilCost;
  }
  
  public Float getDistance() {
    return distance;
  }
  public void setDistance(Float distance) {
    this.distance = distance;
  }
  public String getParentId() {
    return parentId;
  }
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }
  public String getOilRecord() {
    return oilRecord;
  }
  public void setOilRecord(String oilRecord) {
    this.oilRecord = oilRecord;
  }
  public String getRegionIds() {
    return regionIds;
  }
  public void setRegionIds(String regionIds) {
    this.regionIds = regionIds;
  }
  
  
  
}

