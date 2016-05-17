package com.wangge.app.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@Table(name="SYS_CASH_RECORD")
public class Cash implements Serializable  {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  
  public Cash(){
    super();
  }
  
  public Cash(Integer cashId, String userId) {
    this.cashId = cashId;
    this.userId = userId;
    this.createDate = new Date();
    this.status = 0;
  }
  @Id
  @Column(name="id")
  private Integer cashId ; //订单id
//  @Transient
  @OneToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
  @JoinColumn(name="id",insertable =false,updatable=false)
  private OrderSignfor order;//订单
  private String userId ; //用户id
  
  private Integer status;//支付状态
  
  @JsonFormat(pattern="MM.dd HH:mm",timezone = "GMT+8")  
  private Date createDate ;//创建日期
  
  @JsonFormat(pattern="MM.dd HH:mm",timezone = "GMT+8")  
  private Date payDate  ;//支付时间
  
  @Transient
  private List<OrderItem> orderItem;
  
  
  public List<OrderItem> getOrderItem() {
    return orderItem;
  }

  public void setOrderItem(List<OrderItem> orderItem) {
    this.orderItem = orderItem;
  }

  public Integer getCashId() {
    return cashId;
  }
  public void setCashId(Integer id) {
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
