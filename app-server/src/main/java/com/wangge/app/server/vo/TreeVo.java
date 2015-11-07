package com.wangge.app.server.vo;

/**
 * 
 * 功能: 
 * 详细： 	
 * 类名：  树型相关
 * 作者： 	jiabin
 * 版本：  1.0
 * 日期：  2015年11月5日下午4:06:40
 */
public class TreeVo {
	private String id;
	private String pId;//父id
	private boolean isParent;//是否是父节点isParent
	private String icon;//图标
	private String name;//节点名字
	
	private boolean open;//默认打开

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}


	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}
	
}	
