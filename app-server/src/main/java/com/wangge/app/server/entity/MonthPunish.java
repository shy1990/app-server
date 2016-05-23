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
@Table(name="SYS_MONTH_PUNISH_RECORD")
public class MonthPunish implements Serializable  {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  

  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Integer id ; //订单id
//  @Transient
  private String userId ; //用户id
  
  @Column(name="WATER_NO")
  private String seriaNo; //流水单号
  
  @Column(name="DEBT_MONEY")
  private Float debt; //欠款金额
  
  @Column(name="FINE_MONEY")
  private Float amerce; //扣罚金额 
  
  @JsonFormat(pattern="MM.dd HH:mm",timezone = "GMT+8")  
  private Date createDate ;//创建日期
  
  
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getSeriaNo() {
    return seriaNo;
  }
  public void setSeriaNo(String seriaNo) {
    this.seriaNo = seriaNo;
  }
  public Float getDebt() {
    return debt;
  }
  public void setDebt(Float debt) {
    this.debt = debt;
  }
  public Float getAmerce() {
    return amerce;
  }
  public void setAmerce(Float amerce) {
    this.amerce = amerce;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public Date getCreateDate() {
    return createDate;
  }
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  
  
  

}
