package com.wangge.app.server.jpush.client;

import java.util.HashMap;
import java.util.Map;










import com.wangge.app.server.jpush.DeviceEnum;
import com.wangge.app.server.jpush.ErrorCodeEnum;
import com.wangge.app.server.jpush.JPushClient;
import com.wangge.app.server.jpush.MessageResult;



public class JpushClient {
	
		private static final String appKey ="6f270e9813a7f75cc8e5558b";////必填，例如466f7032ac604e02fb7bda89
		private static final String masterSecret = "310d355dc8a3229ea3355fbe";//必填，每个应用都对应一个masterSecret
		private static JPushClient jpush = null;
		
		public static final int MAX = Integer.MAX_VALUE;
		public static final int MIN = (int) MAX/2;
		
		/**
		 * 保持 sendNo 的唯一性是有必要的
		 * It is very important to keep sendNo unique.
		 * @return sendNo
		 */
		private static int getRandomSendNo() {
		    return (int) (MIN + Math.random() * (MAX - MIN));
		}
			
		
		
		/*
		 * 保存离线的时长。秒为单位。最多支持10天（864000秒）。
		 * 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
		 * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒
		 */
		private static long timeToLive =  60 * 60 * 24;  

		public static void main(String[] args) {
			/*
			 * Example1: 初始化,默认发送给android和ios，同时设置离线消息存活时间
			 * jpush = new JPushClient(masterSecret, appKey, timeToLive);
			 * 
			 * Example2: 只发送给android
			 * jpush = new JPushClient(masterSecret, appKey, DeviceEnum.Android);
			 * 
			 * Example3: 只发送给IOS
			 * jpush = new JPushClient(masterSecret, appKey, DeviceEnum.IOS);
			 * 
			 * Example4: 只发送给android,同时设置离线消息存活时间
			 * jpush = new JPushClient(masterSecret, appKey, timeToLive, DeviceEnum.Android);
			 */
			jpush = new JPushClient(masterSecret, appKey, timeToLive, DeviceEnum.Android);
			/* 
			 * 是否启用ssl安全连接, 可选
			 * 参数：启用true， 禁用false，默认为非ssl连接
			 */
			//jpush.setEnableSSL(true);

			//测试发送消息或者通知
			sendOrder("下单通知","会员下单通知:【222222222222222】山东省济南市历下区天桥店下单成功，订单商品","18764157959","20151111111111","天桥魅族手机");
		}
		/**
		 * 
		 * @Description: 订单通知
		 * @param @param title
		 * @param @param msg
		 * @param @param alias
		 * @param @param ordernum
		 * @param @param username
		 * @param @return   
		 * @return String  
		 * @throws
		 * @author changjun
		 * @date 2015年11月13日
		 */
		public static String sendOrder(String title,String msg,String alias,String ordernum,String username) {
		    // 在实际业务中，建议 sendNo 是一个你自己的业务可以处理的一个自增数字。
		    String sendNo=getRandomSendNo()+"";
			Map<String, Object> extra = new HashMap<String, Object>();
			extra.put("ordernum", ordernum);
			extra.put("username", username);
			extra.put("type", "0");
			jpush = new JPushClient(masterSecret, appKey, timeToLive, DeviceEnum.Android);
//			jpush.setEnableSSL(true);
			MessageResult msgResult = null;
			if("all".equalsIgnoreCase(alias)){
				msgResult = jpush.sendNotificationWithAppKey(sendNo, title, msg);
			}else{
				msgResult =jpush.sendNotificationWithAlias(sendNo, alias, title, msg, 1, extra);
			}
			String str = "";
			if (null != msgResult) {
				System.out.println("服务器返回数据: " + msgResult.toString());
				if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
					str = "发送成功， sendNo=" + msgResult.getSendno();
					System.out.println(str);
				} else {
					str = "发送失败， 错误代码=" + msgResult.getErrcode() + ", 错误消息=" + msgResult.getErrmsg();
					System.out.println(str);
				}
			} else {
				str = "无法获取数据";
				System.out.println(str);
			}
			return str;
		}
		/**
		 * 
		 * @Description: 普通通知
		 * @param @param title
		 * @param @param msg
		 * @param @param alias
		 * @param @return   
		 * @return String  
		 * @throws
		 * @author changjun
		 * @date 2015年11月13日
		 */
		public static String sendSimple(String title,String msg,String alias,Long msgId,String type) {
		    // 在实际业务中，建议 sendNo 是一个你自己的业务可以处理的一个自增数字。
		    String sendNo=getRandomSendNo()+"";
			jpush = new JPushClient(masterSecret, appKey, timeToLive, DeviceEnum.Android);
			jpush.setEnableSSL(true);
			Map<String, Object> extra = new HashMap<String, Object>();
			extra.put("msgId", msgId);
			extra.put("type", type);
			MessageResult msgResult = null;
			if("all".equalsIgnoreCase(alias)){
				msgResult = jpush.sendNotificationWithAppKey(sendNo, title, msg);
			}else{
				msgResult = jpush.sendNotificationWithAlias(sendNo, alias, title, msg, 1, extra);
			}
			String str = "";
			if (null != msgResult) {
				System.out.println("服务器返回数据: " + msgResult.toString());
				if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
					str = "发送成功， sendNo=" + msgResult.getSendno();
					System.out.println(str);
				} else {
					str = "发送失败， 错误代码=" + msgResult.getErrcode() + ", 错误消息=" + msgResult.getErrmsg();
					System.out.println(str);
				}
			} else {
				str = "无法获取数据";
				System.out.println(str);
			}
			return str;
		}
}
