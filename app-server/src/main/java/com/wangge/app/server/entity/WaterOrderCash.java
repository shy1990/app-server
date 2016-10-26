package com.wangge.app.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;



/**
 * 
 * @author thor
 *
 */
@Entity
@Table(name="SYS_WATER_ORDER_CASH")
public class WaterOrderCash implements Serializable  {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  
  @Id
  @Column(name="SERIAL_NO")
  private String serialNo ; //流水单号
  
  private String userId ; //用户id
  private Float cashMoney  ;//收现金额
  private Float paymentMoney ;//支付金额
  private Integer isPunish ;//是否扣罚
  
  @OneToMany(fetch=FetchType.LAZY)
  @JoinColumn(name="SERIAL_NO",insertable=false,updatable=false)
  private List<WaterOrderDetail> orderDetailList;//订单详情
  
  private Integer payStatus;//支付状态
  
  @JsonFormat(pattern="MM.dd HH:mm",timezone = "GMT+8")  
  private Date createDate ;//创建日期
  @JsonFormat(pattern="MM.dd HH:mm",timezone = "GMT+8")  
  private Date payDate  ;//支付时间
  
  
  public List<WaterOrderDetail> getOrderDetailList() {
    return orderDetailList;
  }
  public void setOrderDetailList(List<WaterOrderDetail> orderDetailList) {
    this.orderDetailList = orderDetailList;
  }
  public Integer getPayStatus() {
    return payStatus;
  }
  public void setPayStatus(Integer payStatus) {
    this.payStatus = payStatus;
  }
  public void setSerialNo(String serialNo) {
    this.serialNo = serialNo;
  }
	public String getSerialNo() {
		return serialNo;
	}
	public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public Float getCashMoney() {
    return cashMoney;
  }
  public void setCashMoney(Float cashMoney) {
    this.cashMoney = cashMoney;
  }
  public Float getPaymentMoney() {
    return paymentMoney;
  }
  public void setPaymentMoney(Float paymentMoney) {
    this.paymentMoney = paymentMoney;
  }
  public Integer getIsPunish() {
    return isPunish;
  }
  public void setIsPunish(Integer isPunish) {
    this.isPunish = isPunish;
  }
  public Date getCreateDate() {
    return createDate;
  }
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
  public Date getPayDate() {
    return payDate;
  }
  public void setPayDate(Date payDate) {
    this.payDate = payDate;
  }

  

}
