package com.wangge.app.server.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;



/**
 * 
 * @author thor
 *
 */
@Entity
@Table(name="SYS_CASH_RECORD")
public class Cash implements Serializable  {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  
  public Cash(){
    super();
  }
  
  public Cash(Long cashId, String userId) {
    this.cashId = cashId;
    this.userId = userId;
    this.createDate = new Date();
  }
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  @Column(name="id")
  private Long cashId ; //订单id
//  @Transient
  @OneToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
  @JoinColumn(name="id",insertable =false,updatable=false)
  private OrderSignfor order;//订单
  private String userId ; //用户id
  
  private Integer status;//支付状态
  
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern="MM.dd HH:mm",timezone = "GMT+8")  
  @Temporal(TemporalType.TIMESTAMP)
  private Date createDate ;//创建日期
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern="MM.dd HH:mm",timezone = "GMT+8")  
  @Temporal(TemporalType.TIMESTAMP)
  private Date payDate  ;//支付时间
  
  
  
  public Long getCashId() {
    return cashId;
  }
  public void setCashId(Long id) {
    this.cashId = id;
  }
  public OrderSignfor getOrder() {
    return order;
  }
  public void setOrder(OrderSignfor order) {
    this.order = order;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public Integer getStatus() {
    return status;
  }
  public void setStatus(Integer status) {
    this.status = status;
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
