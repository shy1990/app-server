package com.wangge.buzmgt.exception;
/**
 * 
 * 功能: 自定义异常
 * 详细： 	
 * 类名：  MyRuntimeException
 * 作者： 	jiabin
 * 版本：  1.0
 * 日期：  2015年7月15日下午6:23:30
 */
public class MyRuntimeException extends RuntimeException {
	
	/**
	 * @Fields serialVersionUID : 
	 */
	
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 创建一个新的实例 MyException.
	 *
	 * @param message 异常信息
	 */
	public MyRuntimeException(String message) {
		super(message);
	}
}
