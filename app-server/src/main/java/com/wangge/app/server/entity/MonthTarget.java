package com.wangge.app.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 
  * ClassName: MonthTarget <br/> 
  * Reason: TODO 月指标entity. <br/> 
  * date: 2016年6月25日 下午1:6:09 <br/> 
  * 
  * @author Administrator 
  * @version  
  * @since JDK 1.8
 */
@Entity
@Table(name = "SYS_MONTH_TARGET")
public class MonthTarget implements Serializable{

  /**
  * @Fields serialVersionUID : TODO(月指标serial)
  */
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GenericGenerator(name = "idgen",strategy = "increment")
  @GeneratedValue(generator="idgen")
  @Column(name = "TARGET_ID")
  private Long id; //主键id
  
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID")
  private Salesman salesman;
  
  private String managerRegion;//区域经理regionid
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REGION_ID")
  private Region region;
  
  private int orderNum;//提货量
  
  private int merchantNum;//提货商家数量
  
  private int activeNum;//活跃商家
  
  private int matureNum;//成熟商家
  
  @Column(name = "PUBLISH_STATUS")
  private int publishStatus;//0未发布 1已发布
  
  private String targetCycle;//日期指标

  @Transient
  private int order;//实际提货量

  @Transient
  private int active;//实际活跃商家

  @Transient
  private Integer matureAll;//注册商家

  @Transient
  private boolean view;//视图判断

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public int getActive() {
    return active;
  }

  public void setActive(int active) {
    this.active = active;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Salesman getSalesman() {
    return salesman;
  }

  public void setSalesman(Salesman salesman) {
    this.salesman = salesman;
  }

  public Region getRegion() {
    return region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

  public int getOrderNum() {
    return orderNum;
  }

  public void setOrderNum(int orderNum) {
    this.orderNum = orderNum;
  }

  public int getMerchantNum() {
    return merchantNum;
  }

  public void setMerchantNum(int merchantNum) {
    this.merchantNum = merchantNum;
  }

  public int getActiveNum() {
    return activeNum;
  }

  public void setActiveNum(int activeNum) {
    this.activeNum = activeNum;
  }

  public int getMatureNum() {
    return matureNum;
  }

  public void setMatureNum(int matureNum) {
    this.matureNum = matureNum;
  }

  public int getPublishStatus() {
    return publishStatus;
  }

  public void setPublishStatus(int publishStatus) {
    this.publishStatus = publishStatus;
  }

  public String getTargetCycle() {
    return targetCycle;
  }

  public void setTargetCycle(String targetCycle) {
    this.targetCycle = targetCycle;
  }

  public String getManagerRegion() {
    return managerRegion;
  }

  public void setManagerRegion(String managerRegion) {
    this.managerRegion = managerRegion;
  }

  public Integer getMatureAll() {
    return matureAll;
  }

  public void setMatureAll(Integer matureAll) {
    this.matureAll = matureAll;
  }

  public boolean getView() {
    return view;
  }

  public void setView(boolean view) {
    this.view = view;
  }
}
