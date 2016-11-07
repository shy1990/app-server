package com.wangge.app.server.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SYS_ORDER_ITEM")
public class OrderItem implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ORDER_ITEM_ID")
  private String id;
  private String name;

  private String type;
  private Integer nums;
	@Column(name = "ORDER_NUM")
  private String orderNum;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ORDER_NUM",updatable = false, insertable = false)
  private Order order;

  public String getId() {
    return id;
  }
  
  
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }


  public Integer getNums() {
    return nums;
  }


  public void setNums(Integer nums) {
    this.nums = nums;
  }

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
}
