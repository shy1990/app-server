package com.wangge.app.server.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
@Table(name="BIZ_UNPAYMENT_REMARK")
public class UnpaymentRemark {
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Integer  id;
  private String aboveImgUrl;
  private String frontImgUrl;
  private String sideImgUrl;
  private String shopName;
  private String orderno;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern="MM.dd HH:mm",timezone = "GMT+8")  
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime = new Date();
  private Integer status;
  private String remark;
  @JsonIgnore 
  private String salesmanId;
  
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getAboveImgUrl() {
    return aboveImgUrl;
  }
  public void setAboveImgUrl(String aboveImgUrl) {
    this.aboveImgUrl = aboveImgUrl;
  }
  public String getFrontImgUrl() {
    return frontImgUrl;
  }
  public void setFrontImgUrl(String frontImgUrl) {
    this.frontImgUrl = frontImgUrl;
  }
  public String getSideImgUrl() {
    return sideImgUrl;
  }
  public void setSideImgUrl(String sideImgUrl) {
    this.sideImgUrl = sideImgUrl;
  }
  public String getShopName() {
    return shopName != null ? shopName : "";
  }
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }
  public String getOrderno() {
    return orderno != null ? orderno : "";
  }
  public void setOrderno(String orderno) {
    this.orderno = orderno;
  }
 
  public Date getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
  public Integer getStatus() {
    return status;
  }
  public void setStatus(Integer status) {
    this.status = status;
  }
  public String getSalesmanId() {
    return salesmanId;
  }
  public void setSalesmanId(String salesmanId) {
    this.salesmanId = salesmanId;
  }
  public String getRemark() {
    return remark != null ? remark : "";
  }
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
}
