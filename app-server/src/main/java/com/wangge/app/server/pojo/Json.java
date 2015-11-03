
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
}
