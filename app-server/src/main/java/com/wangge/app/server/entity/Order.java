package com.wangge.app.server.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;


@Entity
@Table(name = "SYS_ORDER")
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
	  PAY_ONLINE("在线支付"),PAY_OFFLINE("货到付款"),POS("POS支付");
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
	@Column(name = "PAY_STATUS")
	private String payStatus;

	@Column(name = "pay_ment")
	private PayMent payMent; // 支付方式

	@Column(name = "deal_type")
	private String dealType; // 支付方式

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	private Collection<OrderItem> items;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	private Region region;
	@Column(name = "mobile")
	private String mobile;// 客户手机号
	@Column(name = "member_id")
	private String memberId;// b2b商城用户id
	@Column(name = "actual_pay_num")
	private BigDecimal actualPayNum;//实付金额
	
	@Column(name = "wallet_pay_no")
	private String walletPayNo;//钱包支付流水号

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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getDealType() {
		if (dealType == null) {
			this.dealType = "未付款";
		}
		if("yeePay".equals(dealType)){
			this.dealType = "线上支付"; //最开始版本叫：线上支付　现在：在线支付
		}
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public BigDecimal getActualPayNum() {
		return actualPayNum;
	}

	public void setActualPayNum(BigDecimal actualPayNum) {
		this.actualPayNum = actualPayNum;
	}

	public String getWalletPayNo() {
		return walletPayNo;
	}

	public void setWalletPayNo(String walletPayNo) {
		this.walletPayNo = walletPayNo;
	}
	
}
