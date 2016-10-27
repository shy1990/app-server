package com.wangge.app.server.vo;

import java.util.List;

public class BillVo {
	
	private Float totalArrears;
	
	private List<OrderVo> content;
	
	private int totalPages;

	public Float getTotalArrears() {
		return totalArrears;
	}

	public void setTotalArrears(Float totalArrears) {
		this.totalArrears = totalArrears;
	}

	public List<OrderVo> getContent() {
		return content;
	}

	public void setContent(List<OrderVo> content) {
		this.content = content;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	
}
