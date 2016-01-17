package com.wangge.app.server.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wangge.common.entity.Region;

@Entity
@Table(name = "BIZ_ORDER")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum ShipStatus {
		NO_SEND("未发货"), SENDED("已发货"), SALESMAN_RESIVED("业务签收"), MEMBER_RESIVED("客户签收"),MEMBER_REFUSE("客户拒收");
		private String name;

		private ShipStatus(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	
	public enum PayMent{
	  PAY_ONLINE("线上支付"),PAY_OFFLINE("货到付款"),POS("POS支付");
	  private String name;

    private PayMent(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
	}
	
	@Id
	@Column(name = "ORDER_NUM")
	private String id;
	private String shopName;// 店铺名
	private BigDecimal amount; // 订单金额
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;// 下单时间
	@Enumerated(EnumType.ORDINAL)
	@Column(name="SHIP_STATUS")
	private ShipStatus status;
	
	@Column(name="pay_ment")
	private PayMent payMent; //支付方式
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	private Collection<OrderItem> items;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	private Region region;
	@Column(name="mobile")
	private String mobile;//客户手机号
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public PayMent getPayMent() {
    return payMent;
  }

  public void setPayMent(PayMent payMent) {
    this.payMent = payMent;
  }

  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public ShipStatus getStatus() {
		return status;
	}

	public void setStatus(ShipStatus status) {
		this.status = status;
	}

	public Collection<OrderItem> getItems() {
		return items;
	}

	public void setItems(Collection<OrderItem> items) {
		this.items = items;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

}
