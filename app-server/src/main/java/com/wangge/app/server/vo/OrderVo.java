package com.wangge.app.server.vo;

import com.wangge.app.server.entity.dto.OrderDto;

import java.util.List;

public class OrderVo {
	
	/*Float historyArrears;
	Float  yesterdayArrears;
	Float todayArrears;*/
	
	private String shopName;
	
	private Float totalArreas;
	
	private List<OrderDto> content;


	/*public Float getHistoryArrears() {
		return historyArrears;
	}

	public void setHistoryArrears(Float historyArrears) {
		this.historyArrears = historyArrears;
	}

	public Float getYesterdayArrears() {
		return yesterdayArrears;
	}

	public void setYesterdayArrears(Float yesterdayArrears) {
		this.yesterdayArrears = yesterdayArrears;
	}

	public Float getTodayArrears() {
		return todayArrears;
	}

	public void setTodayArrears(Float todayArrears) {
		this.todayArrears = todayArrears;
	}*/


	public String getShopName() {
		return shopName;
	}

	public List<OrderDto> getContent() {
		return content;
	}

	public void setContent(List<OrderDto> content) {
		this.content = content;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Float getTotalArreas() {
		return totalArreas;
	}

	public void setTotalArreas(Float totalArreas) {
		this.totalArreas = totalArreas;
	}
	
}
