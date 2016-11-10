package com.wangge.app.server.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 收现金商家分组POJO
 * CashShopGroup
 *
 * @author ChenGuop
 * @date 2016/11/8
 */
public class CashShopGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	private String shopName;
	private Float totalMoney;//总金额
	private List<CashPart> CashParts;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public List<CashPart> getCashParts() {
		return CashParts;
	}

	public void setCashParts(List<CashPart> cashParts) {
		CashParts = cashParts;
	}

	public Float getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Float totalMoney) {
		this.totalMoney = totalMoney;
	}

	@Override
	public String toString() {
		return "CashShopGroup{" +
						"shopName='" + shopName + '\'' +
						", totalMoney=" + totalMoney +
						", CashParts=" + CashParts +
						'}';
	}
}
