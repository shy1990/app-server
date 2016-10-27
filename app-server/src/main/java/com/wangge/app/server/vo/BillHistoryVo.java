package com.wangge.app.server.vo;

import java.math.BigDecimal;
import java.util.List;

import com.wangge.app.server.entity.dto.BillHistoryDto;

public class BillHistoryVo {
	
	private Float totalArrears;
	
	private int totalPages;
	
	private List<BillHistoryDto> content;


	public Float getTotalArrears() {
		return totalArrears;
	}

	public void setTotalArrears(Float totalArrears) {
		this.totalArrears = totalArrears;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<BillHistoryDto> getContent() {
		return content;
	}

	public void setContent(List<BillHistoryDto> content) {
		this.content = content;
	}

}
