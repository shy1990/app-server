package com.wangge.app.server.pojo;

import java.io.Serializable;

/**
 * 
* @ClassName: Json
* @Description: TODO(这里用一句话描述这个类的作用)
* @author ZhouZhangbao
* @date 2014-9-4 下午8:03:25
 */
public class Json implements Serializable{
	private static final long serialVersionUID = 1L;
	private Boolean success=false;//是否成功
	private String msg;
	private Object obj;
	private String id;
	private String phone;
	private String regionId;
	private Integer status; 
	
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
