package com.wangge.app.server.entity;
/**
 * �Ա�
 * @author dell
 *
 */
public enum SalesmanStatus {
	saojie(1), kaifa(2), weihu(3),zhuanzheng(4),shenhe(5);

	private Integer num;

	private SalesmanStatus(Integer num) {
		this.num = num;
	}

	public Integer getNum() {
		return num;
	}
}
